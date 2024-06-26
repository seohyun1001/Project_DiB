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
public class RestaurantDTO {
    private Long rno;
    private String rest_name;
    private String rest_exp;
    private String rest_exp2;
    private String rest_loc;
    private String rest_phone;
    private String rest_menu;
    private String rest_time;
    private String rest_image;
    private LocalDateTime moddate;
    private LocalDateTime regdate;

}
