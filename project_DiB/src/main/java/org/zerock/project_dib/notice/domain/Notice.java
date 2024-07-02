package org.zerock.project_dib.notice.domain;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Notice {
    private Long nno;
    private String noticeTitle;
    private String noticeContent;
    private String noticeImage;
    private LocalDate regdate = LocalDate.now();
    private LocalDate moddate = LocalDate.now();
}
