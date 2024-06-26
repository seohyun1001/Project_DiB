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
    private Long reviewNo;
    private String reviewText;
    private String mid;
    private boolean reviewLike;
    private Long rno;
    private LocalDateTime moddate;
    private LocalDateTime regdate;
}
