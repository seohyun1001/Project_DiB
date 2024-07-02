package org.zerock.project_dib.pse.domain;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class Notice {
    private Long nno; // 공지사항 번호
    private String noticeTitle;
    private String noticeContent;
    private String noticeImage; // 공지사항 이미지 경로
    private LocalDate regdate = LocalDate.now();
    private LocalDate moddate = LocalDate.now();
}
