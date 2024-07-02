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
public class One2oneDTO {
    private Long otono; // 1대1 문의 번호
    private String one2oneTitle;
    private String one2oneContent;
    private MultipartFile one2oneImage; // 1대1 문의 이미지 파일
    private LocalDate regdate;
    private LocalDate moddate;
    private String one2oneImagePath; // 1대1 문의 이미지 경로
    private List<String> fileNames; // 파일 이름 목록
    private String mid; // 작성자 ID
}