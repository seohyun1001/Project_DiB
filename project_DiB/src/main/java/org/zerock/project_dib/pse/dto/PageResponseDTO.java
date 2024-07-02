package org.zerock.project_dib.pse.dto;

import lombok.*;

import java.util.List;

@Data
@ToString
public class PageResponseDTO<T> {
    private PageRequestDTO pageRequestDTO; // 페이징 요청 정보를 담는 객체
    private List<T> dtoList;  // 검색시 데이터 리스트
    private int totalPage; // 총 페이지 수
    private int total; // 전체 데이터 수

    // 시작 페이지 번호
    private int start;
    // 끝 페이지 번호
    private int end;

    // 이전 페이지 존재 여부
    private boolean prev;
    // 다음 페이지 존재 여부
    private boolean next;

    // 빌더를 사용하여 객체를 생성하는 생성자
    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<T> dtoList, int total) {
        this.pageRequestDTO = pageRequestDTO;
        this.dtoList = dtoList;
        this.total = total;

        // 총 페이지 수 계산
        this.totalPage = (int) Math.ceil(total / (double) pageRequestDTO.getSize());


        // 페이지 번호 계산 로직
        int currentPage = pageRequestDTO.getPage();
        int tempEnd = (int) (Math.ceil(currentPage / 10.0)) * 10; // 임시 끝 페이지 번호
        this.start = tempEnd - 9; // 시작 페이지 번호
        int realEnd = (int) Math.ceil((double) total / pageRequestDTO.getSize()); // 실제 끝 페이지 번호
        this.end = Math.min(tempEnd, realEnd);

        // 이전 페이지와 다음 페이지 존재 여부 계산
        this.prev = this.start > 1;
        this.next = this.end < realEnd;

//        // 끝 페이지 번호 계산
//        this.end = (int) (Math.ceil(pageRequestDTO.getPage() / 10.0)) * 10;
//        // 시작 페이지 번호 계산
//        this.start = this.end - 9;
//        int last = (int) (Math.ceil((double) total / pageRequestDTO.getSize()));
//        // 끝 페이지 번호 조정
//        this.end = Math.min(this.end, last);
//
//        // 이전 페이지 존재 여부 계산
//        this.prev = this.start > 1;
//        // 다음 페이지 존재 여부 계산
//        this.next = this.total > this.end * this.pageRequestDTO.getSize();
    }
}
