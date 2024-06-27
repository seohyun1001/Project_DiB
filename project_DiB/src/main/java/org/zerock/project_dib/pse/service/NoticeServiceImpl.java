package org.zerock.project_dib.pse.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.project_dib.pse.domain.Notice;
import org.zerock.project_dib.pse.dto.NoticeDTO;
import org.zerock.project_dib.pse.dto.PageRequestDTO;
import org.zerock.project_dib.pse.dto.PageResponseDTO;
import org.zerock.project_dib.pse.mapper.NoticeMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeMapper noticeMapper;
    private final ModelMapper modelMapper;
    private static final String UPLOAD_DIR = "C:/upload/";

    @Override
    public Long register(NoticeDTO noticeDTO) {
        Notice notice = dtoToEntity(noticeDTO);
        notice.setRegdate(LocalDate.now());
        notice.setModdate(LocalDate.now());

        MultipartFile file = noticeDTO.getNoticeImage();
        if (file != null && !file.isEmpty()) {
            try {
                String filePath = UPLOAD_DIR + file.getOriginalFilename();
                Files.createDirectories(Paths.get(UPLOAD_DIR));
                file.transferTo(new File(filePath));
                notice.setNoticeImage(filePath); // 파일 이름만 저장
            } catch (IOException e) {
                e.printStackTrace();
                return notice.getNno();
            }
        }

        noticeMapper.insert(notice);
        return notice.getNno();
    }

//    @Override
//    public NoticeDTO readOne(Long nno) {
//        Notice notice = noticeMapper.read(nno);
//        return modelMapper.map(notice, NoticeDTO.class);
//    }

    @Override
    public NoticeDTO readOne(Long nno) {
        Notice notice = noticeMapper.read(nno);
        NoticeDTO noticeDTO = modelMapper.map(notice, NoticeDTO.class);
        if (notice.getNoticeImage() != null) {
            noticeDTO.setNoticeImagePath("/upload/" + notice.getNoticeImage());
        }
        return noticeDTO;
    }

//    @Override
//    public void modify(NoticeDTO noticeDTO) {
//        Notice notice = modelMapper.map(noticeDTO, Notice.class);
//        notice.setModdate(LocalDate.now()); // 수정일자를 현재 날짜로 설정
//        noticeMapper.update(notice);
//    }

    @Override
    public void modify(NoticeDTO noticeDTO) {
        Notice notice = modelMapper.map(noticeDTO, Notice.class);
        notice.setModdate(LocalDate.now()); // 수정일자를 현재 날짜로 설정

        MultipartFile file = noticeDTO.getNoticeImage();
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = file.getOriginalFilename();
                String filePath = UPLOAD_DIR + fileName;
                Files.createDirectories(Paths.get(UPLOAD_DIR));
                file.transferTo(new File(filePath));
                notice.setNoticeImage(fileName);  // 파일 이름만 저장
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Updating Notice: " + notice);
        noticeMapper.update(notice);
    }

    @Override
    public void remove(Long nno) {
        noticeMapper.delete(nno);
    }

    @Override
    public PageResponseDTO<NoticeDTO> getAllNotices(PageRequestDTO pageRequestDTO) {
        List<Notice> notices = noticeMapper.getAll(pageRequestDTO);
        List<NoticeDTO> dtoList = notices.stream()
                .map(notice -> modelMapper.map(notice, NoticeDTO.class))
                .collect(Collectors.toList());
        int total = noticeMapper.getCount();
        return PageResponseDTO.<NoticeDTO>builder()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalPage((int) Math.ceil((double) total / pageRequestDTO.getSize()))
                .build();
    }
}
