package org.zerock.project_dib.pse.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class NoticeDTO {
    private Long nno;
    private String noticeTitle;
    private String noticeContent;
    private MultipartFile noticeImage;
    private LocalDate regdate;
    private LocalDate moddate;
    private String noticeImagePath;
    private List<String> fileNames;
}