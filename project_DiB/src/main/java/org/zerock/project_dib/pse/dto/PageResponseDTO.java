package org.zerock.project_dib.pse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResponseDTO<T> {
    private PageRequestDTO pageRequestDTO;
    private List<T> dtoList;
    private int totalPage;
    private int total;

    // 시작 페이지 번호
    private int start;
    // 끝 페이지 번호
    private int end;

    // 이전 페이지 존재 여부
    private boolean prev;
    // 다음 페이지 존재 여부
    private boolean next;

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<T> dtoList, int total) {
        this.pageRequestDTO = pageRequestDTO;
        this.dtoList = dtoList;
        this.total = total;

        this.totalPage = (int) Math.ceil(total / (double) pageRequestDTO.getSize());
        this.end = (int) (Math.ceil(pageRequestDTO.getPage() / 10.0)) * 10;
        this.start = this.end - 9;
        int last = (int) (Math.ceil((double) total / pageRequestDTO.getSize()));
        this.end = Math.min(this.end, last);

        this.prev = this.start > 1;
        this.next = this.total > this.end * this.pageRequestDTO.getSize();
    }
}
