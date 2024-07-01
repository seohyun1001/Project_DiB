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
    private Long nno;
    private String noticeTitle;
    private String noticeContent;
    private MultipartFile noticeImage;
    private LocalDate regdate;
    private LocalDate moddate;
    private String noticeImagePath;
    private List<String> fileNames;
}