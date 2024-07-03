package org.zerock.project_dib.pse.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class One2one {
    private Long otono; // 1대1 문의 번호
    private String one2oneTitle;
    private String one2oneContent;
    private String one2oneImage; // 1대1 문의 이미지 경로
    private LocalDate regdate = LocalDate.now();
    private LocalDate moddate = LocalDate.now();
    private String mid; // 작성자 ID
}
