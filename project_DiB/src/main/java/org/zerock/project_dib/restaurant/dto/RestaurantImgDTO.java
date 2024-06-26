package org.zerock.project_dib.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantImgDTO {
    private String uuid;
    private String fileName;
    private Long rno;
}
