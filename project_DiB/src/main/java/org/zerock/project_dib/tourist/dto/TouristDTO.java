package org.zerock.project_dib.tourist.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class TouristDTO {

    private int tno;

    private String tourName;

    private String tourAddr;

    private String tourOpentime;

    private String tourClosetime;

    private boolean tourParking;

    private String tourContent;

    private LocalDateTime modDate;

    private LocalDateTime regDate;
}