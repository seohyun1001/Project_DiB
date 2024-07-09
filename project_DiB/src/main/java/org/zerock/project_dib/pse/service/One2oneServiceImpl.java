package org.zerock.project_dib.pse.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.project_dib.mapper.One2oneMapper;
import org.zerock.project_dib.pse.domain.One2one;
import org.zerock.project_dib.pse.dto.One2oneDTO;
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
public class One2oneServiceImpl implements One2oneService {

    private final One2oneMapper one2oneMapper;
    private final ModelMapper modelMapper;
    private static final String UPLOAD_DIR = "C:/upload/";

    @Override
    public Long register(One2oneDTO one2oneDTO) {
        One2one one2one = dtoToEntity(one2oneDTO);
        one2one.setRegdate(LocalDate.now());
        one2one.setModdate(LocalDate.now());

        MultipartFile file = one2oneDTO.getOne2oneImage();
        if (file != null && !file.isEmpty()) {
            try {
                String filePath = UPLOAD_DIR + file.getOriginalFilename();
                Files.createDirectories(Paths.get(UPLOAD_DIR));
                file.transferTo(new File(filePath));
                one2one.setOne2oneImage(filePath); // 파일 이름만 저장
            } catch (IOException e) {
                e.printStackTrace();
                return one2one.getOtono();
            }
        }

        one2oneMapper.insert(one2one);
        return one2one.getOtono();
    }

    @Override
    public One2oneDTO readOne(Long otono) {
        One2one one2one = one2oneMapper.read(otono);
        One2oneDTO one2oneDTO = modelMapper.map(one2one, One2oneDTO.class);
        if (one2one.getOne2oneImage() != null) {
            one2oneDTO.setOne2oneImagePath("/upload/" + one2one.getOne2oneImage());
        }
        return one2oneDTO;
    }

    @Override
    public void modify(One2oneDTO one2oneDTO) {
        One2one one2one = modelMapper.map(one2oneDTO, One2one.class);
        one2one.setModdate(LocalDate.now()); // 수정일자를 현재 날짜로 설정

        MultipartFile file = one2oneDTO.getOne2oneImage();
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = file.getOriginalFilename();
                String filePath = UPLOAD_DIR + fileName;
                Files.createDirectories(Paths.get(UPLOAD_DIR));
                file.transferTo(new File(filePath));
                one2one.setOne2oneImage(fileName);  // 파일 이름만 저장
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Updating One2one: " + one2one);
        one2oneMapper.update(one2one);
    }

    @Override
    public void remove(Long otono) {
        one2oneMapper.delete(otono);
    }

    @Override
    public List<One2oneDTO> getAllOne2ones() {
        List<One2oneDTO>  list = one2oneMapper.selectAll().stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public PageResponseDTO<One2oneDTO> search(PageRequestDTO pageRequestDTO) {
        int total = one2oneMapper.totalCount(pageRequestDTO);
        List<One2one> one2ones = one2oneMapper.search(pageRequestDTO);
        List<One2oneDTO> dtoList = one2ones.stream()
                .map(one2one -> {
                    One2oneDTO one2oneDTO = modelMapper.map(one2one, One2oneDTO.class);
//                    one2oneDTO.setFileNames(one2one.getImageSet().stream()
//                            .map(image -> image.getUuid() + "_" + image.getFile_name())
//                            .collect(Collectors.toList()));
                    return one2oneDTO;
                })
                .collect(Collectors.toList());

        return PageResponseDTO.<One2oneDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }

    private One2one dtoToEntity(One2oneDTO one2oneDTO) {
        return One2one.builder()
                .one2oneTitle(one2oneDTO.getOne2oneTitle())
                .one2oneContent(one2oneDTO.getOne2oneContent())
                .mid(one2oneDTO.getMid()) // 작성자 ID
                .build();
    }

    private One2oneDTO entityToDto(One2one one2one) {
        return One2oneDTO.builder()
                .otono(one2one.getOtono())
                .one2oneTitle(one2one.getOne2oneTitle())
                .one2oneContent(one2one.getOne2oneContent())
                .mid(one2one.getMid()) // 작성자 ID
//                .fileNames(one2one.getImageSet().stream()
//                        .map(image -> image.getUuid() + "_" + image.getFile_name())
//                        .collect(Collectors.toList()))
                .build();
    }
    
}
