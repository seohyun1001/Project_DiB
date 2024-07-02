package org.zerock.project_dib.restaurant.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {
    private int rno;
    private String rest_name;
    private String rest_exp;
    private String rest_exp2;
    private String rest_loc;
    private String rest_phone;
    private String rest_menu;
    private String rest_time;
    private LocalDateTime moddate;
    private LocalDateTime regdate;
    private List<String> fileNames;
}
