package org.zerock.project_dib.tourist.domain;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Tourist {

    private int tno;

    private String tourName;

    private String tourAddr;

    private String tourOpentime;

    private String tourClosetime;

    private boolean tourParking;

    private String tourContent;

    private String tourImage;

    private LocalDateTime modDate;

    private LocalDateTime regDate;
}
