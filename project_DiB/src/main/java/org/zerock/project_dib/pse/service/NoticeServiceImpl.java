package org.zerock.project_dib.pse.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.project_dib.mapper.NoticeMapper;
import org.zerock.project_dib.pse.domain.Notice;
import org.zerock.project_dib.pse.dto.NoticeDTO;
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

    @Override
    public NoticeDTO readOne(Long nno) {
        Notice notice = noticeMapper.read(nno);
        NoticeDTO noticeDTO = modelMapper.map(notice, NoticeDTO.class);
        if (notice.getNoticeImage() != null) {
            noticeDTO.setNoticeImagePath("/upload/" + notice.getNoticeImage());
        }
        return noticeDTO;
    }

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
    public List<NoticeDTO> getAllNotices() {
        return noticeMapper.selectAll().stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PageResponseDTO<NoticeDTO> search(PageRequestDTO pageRequestDTO) {
        int total = noticeMapper.totalCount(pageRequestDTO);
        List<Notice> notices = noticeMapper.search(pageRequestDTO);
        List<NoticeDTO> dtoList = notices.stream()
                .map(notice -> {
                    NoticeDTO noticeDTO = modelMapper.map(notice, NoticeDTO.class);
//                    noticeDTO.setFileNames(notice.getImageSet().stream()
//                            .map(image -> image.getUuid() + "_" + image.getFile_name())
//                            .collect(Collectors.toList()));
                    return noticeDTO;
                })
                .collect(Collectors.toList());

        return PageResponseDTO.<NoticeDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }

    private Notice dtoToEntity(NoticeDTO noticeDTO) {
        return Notice.builder()
                .noticeTitle(noticeDTO.getNoticeTitle())
                .noticeContent(noticeDTO.getNoticeContent())
                .build();
    }

    private NoticeDTO entityToDto(Notice notice) {
        return NoticeDTO.builder()
                .nno(notice.getNno())
                .noticeTitle(notice.getNoticeTitle())
                .noticeContent(notice.getNoticeContent())
//                .fileNames(notice.getImageSet().stream()
//                        .map(image -> image.getUuid() + "_" + image.getFile_name())
//                        .collect(Collectors.toList()))
                .build();
    }


//    @Override
//    public PageResponseDTO<NoticeDTO> getAllNotices(PageRequestDTO pageRequestDTO) {
//
//        // 공지사항 목록 조회
//        List<Notice> notices = noticeMapper.getAll(pageRequestDTO);
//        List<NoticeDTO> dtoList = notices.stream()
//                .map(notice -> modelMapper.map(notice, NoticeDTO.class))
//                .collect(Collectors.toList());
//
//        // 공지사항 총 개수 조회
//        int total = noticeMapper.getCount(pageRequestDTO);
//        return PageResponseDTO.<NoticeDTO>builder()
//                .dtoList(dtoList)
//                .pageRequestDTO(pageRequestDTO)
//                .totalPage((int) Math.ceil((double) total / pageRequestDTO.getSize())) // 총 페이지 수 계산
//                .build();
//    }
}
