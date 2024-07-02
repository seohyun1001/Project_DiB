package org.zerock.project_dib.pse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDTO {
    private Long nno; // 공지사항 번호
    private String noticeTitle;
    private String noticeContent;
    private MultipartFile noticeImage; // 공지사항 이미지 파일
    private LocalDate regdate;
    private LocalDate moddate;
    private String noticeImagePath; // 공지사항 이미지 경로
    private List<String> fileNames; // 파일 이름 목록
}