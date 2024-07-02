package org.zerock.project_dib.accommodation.dto;

import lombok.*;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccommodationImgDTO {

    private String uuid;

    private String file_name;

    private int ano;

    private int ord;

}
