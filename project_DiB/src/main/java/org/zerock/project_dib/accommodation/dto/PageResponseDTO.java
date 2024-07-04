package org.zerock.project_dib.accommodation.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDTO<E> {

  private List<E> dtoList;
  private int totalPage;
  private int page;
  private int size;
  private int start;
  private int end;
  private boolean prev;
  private boolean next;
  private int total;

  public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total) {
    this.dtoList = dtoList;
    this.total = total;
    this.size = pageRequestDTO.getSize();
    this.totalPage = (int) Math.ceil((double) total / size);
    this.page = pageRequestDTO.getPage();
    this.end = (int) (Math.ceil(this.page / 10.0)) * 10;
    this.start = this.end - 9;
    this.prev = this.start > 1;
    this.next = this.totalPage > this.end;
    this.end = this.end > this.totalPage ? this.totalPage : this.end;
  }
}
















