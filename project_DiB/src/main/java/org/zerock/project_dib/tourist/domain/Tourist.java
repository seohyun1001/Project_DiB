package org.zerock.project_dib.tourist.domain;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Tourist {

    private int tno;

    private String tour_name;

    private String tour_addr;

    private String tour_opentime;

    private String tour_closetime;

    private boolean tour_parking;

    private String tour_content;

    private LocalDateTime modDate;

    private LocalDateTime regDate;
}