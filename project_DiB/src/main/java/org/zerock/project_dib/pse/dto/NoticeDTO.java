package org.zerock.project_dib.pse.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class NoticeDTO {
    private Long nno;
    private String noticeTitle;
    private String noticeContent;
    private String noticeImage;
    private LocalDate regdate;
    private LocalDate moddate;
    private List<String> fileNames;
}