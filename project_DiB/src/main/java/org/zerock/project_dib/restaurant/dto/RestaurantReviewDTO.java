package org.zerock.project_dib.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantReviewDTO {
    private Long review_no;
    private String review_text;
    private String mid;
    private boolean review_like;
    private Long rno;
    private LocalDateTime moddate;
    private LocalDateTime regdate;
}
