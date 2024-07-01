package org.zerock.project_dib.pse.domain;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class Notice {
    private Long nno;
    private String noticeTitle;
    private String noticeContent;
    private String noticeImage;
    private LocalDate regdate = LocalDate.now();
    private LocalDate moddate = LocalDate.now();
}
