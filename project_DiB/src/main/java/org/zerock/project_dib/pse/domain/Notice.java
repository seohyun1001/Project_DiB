package org.zerock.project_dib.pse.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 매개변수를 받는 생성자
public class Notice {
    private Long nno; // 공지사항 번호
    private String noticeTitle;
    private String noticeContent;
    private String noticeImage; // 공지사항 이미지 경로
    private LocalDate regdate = LocalDate.now();
    private LocalDate moddate = LocalDate.now();
}
