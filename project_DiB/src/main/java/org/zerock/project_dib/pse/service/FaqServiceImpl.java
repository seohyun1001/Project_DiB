package org.zerock.project_dib.pse.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.project_dib.mapper.FaqMapper;
import org.zerock.project_dib.pse.domain.Faq;
import org.zerock.project_dib.pse.dto.FaqDTO;
import org.zerock.project_dib.pse.dto.PageRequestDTO;
import org.zerock.project_dib.pse.dto.PageResponseDTO;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService {

    private final FaqMapper faqMapper;
    private final ModelMapper modelMapper;
    private static final String UPLOAD_DIR = "C:/upload/";

    @Override
    public Long register(FaqDTO faqDTO) {
        Faq faq = dtoToEntity(faqDTO);
        faq.setRegdate(LocalDate.now());
        faq.setModdate(LocalDate.now());

        MultipartFile file = faqDTO.getFaqImage();
        if (file != null && !file.isEmpty()) {
            try {
                String filePath = UPLOAD_DIR + file.getOriginalFilename();
                Files.createDirectories(Paths.get(UPLOAD_DIR));
                file.transferTo(new File(filePath));
                faq.setFaqImage(filePath); // 파일 이름만 저장
            } catch (IOException e) {
                e.printStackTrace();
                return faq.getFno();
            }
        }

        faqMapper.insert(faq);
        return faq.getFno();
    }

    @Override
    public FaqDTO readOne(Long fno) {
        Faq faq = faqMapper.read(fno);
        FaqDTO faqDTO = modelMapper.map(faq, FaqDTO.class);
        if (faq.getFaqImage() != null) {
            faqDTO.setFaqImagePath("/upload/" + faq.getFaqImage());
        }
        return faqDTO;
    }

    @Override
    public void modify(FaqDTO faqDTO) {
        Faq faq = modelMapper.map(faqDTO, Faq.class);
        faq.setModdate(LocalDate.now()); // 수정일자를 현재 날짜로 설정

        MultipartFile file = faqDTO.getFaqImage();
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = file.getOriginalFilename();
                String filePath = UPLOAD_DIR + fileName;
                Files.createDirectories(Paths.get(UPLOAD_DIR));
                file.transferTo(new File(filePath));
                faq.setFaqImage(fileName);  // 파일 이름만 저장
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Updating Faq: " + faq);
        faqMapper.update(faq);
    }

    @Override
    public void remove(Long fno) {
        faqMapper.delete(fno);
    }

    @Override
    public List<FaqDTO> getAllFaqs() {
        return faqMapper.selectAll().stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PageResponseDTO<FaqDTO> search(PageRequestDTO pageRequestDTO) {
        int total = faqMapper.totalCount(pageRequestDTO);
        List<Faq> faqs = faqMapper.search(pageRequestDTO);
        List<FaqDTO> dtoList = faqs.stream()
                .map(faq -> {
                    FaqDTO faqDTO = modelMapper.map(faq, FaqDTO.class);
//                    faqDTO.setFileNames(faq.getImageSet().stream()
//                            .map(image -> image.getUuid() + "_" + image.getFile_name())
//                            .collect(Collectors.toList()));
                    return faqDTO;
                })
                .collect(Collectors.toList());

        return PageResponseDTO.<FaqDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }

    private Faq dtoToEntity(FaqDTO faqDTO) {
        return Faq.builder()
                .faqTitle(faqDTO.getFaqTitle())
                .faqContent(faqDTO.getFaqContent())
                .build();
    }

    private FaqDTO entityToDto(Faq faq) {
        return FaqDTO.builder()
                .fno(faq.getFno())
                .faqTitle(faq.getFaqTitle())
                .faqContent(faq.getFaqContent())
//                .fileNames(faq.getImageSet().stream()
//                        .map(image -> image.getUuid() + "_" + image.getFile_name())
//                        .collect(Collectors.toList()))
                .build();
    }
    
}
