package org.zerock.project_dib.accommodation.dto;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccommodationDTO {

    private Long ano;
    private String acc_name;
    private String acc_exp;
    private String acc_loc;
    private String acc_phone;
    private boolean acc_like;
    private int acc_total_like;
    private LocalDateTime moddate;
    private LocalDateTime regdate;

}
