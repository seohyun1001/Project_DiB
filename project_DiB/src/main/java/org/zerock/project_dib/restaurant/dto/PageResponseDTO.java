package org.zerock.project_dib.restaurant.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResponseDTO<E> {
    private int page;
    private int size;
    private int total;

    private int start;
    private int end;

    private boolean prev;
    private boolean next;
    private List<E> dtoList;
    private int last;

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total) {
        if(total <= 0){
            return;
        }
        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();
        this.total = total;
        this.dtoList = dtoList;
        this.end = (int)(Math.ceil(this.page/10.0))*10;
        this.start = this.end-9;
        this.last = (int)(Math.ceil((total/(double)size)));
        this.end = end>last?last:end;
        this.prev= this.start>1;
        this.next = total > this.end * this.size;
    }
}
