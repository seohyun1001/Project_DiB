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
public class RestaurantScoreDTO {
    private Long sno;
    private Long review_no;
    private String mid;
    private Long score;
    private LocalDateTime moddate;
    private LocalDateTime regdate;
}
