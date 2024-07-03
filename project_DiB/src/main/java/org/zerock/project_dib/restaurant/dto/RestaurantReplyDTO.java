package org.zerock.project_dib.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantReplyDTO {
    private int review_no;
    private String review_text;
    private String mid;
    private boolean review_like;
    private int rno;
    private LocalDateTime regdate;
    private LocalDateTime moddate;
}
