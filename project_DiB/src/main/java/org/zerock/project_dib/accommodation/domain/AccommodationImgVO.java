package org.zerock.project_dib.accommodation.domain;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccommodationImgVO {

    private String uuid;

    private String file_name;

    private Long ano;

    private int ord;

}
