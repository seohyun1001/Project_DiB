package org.zerock.project_dib.accommodation.service;

import org.zerock.project_dib.accommodation.dto.AccommodationDTO;
import org.zerock.project_dib.accommodation.dto.AccommodationImgDTO;

import java.util.List;

public interface AccommodationService {

    void insertAccommodation(AccommodationDTO accommodationDTO);

    List<AccommodationDTO> accList();

    AccommodationDTO accInfo(int ano);

    void modify(AccommodationDTO accommodationDTO);

    void delete(int ano);

    void insertFile(AccommodationImgDTO accommodationImgDTO);

    void modifyFile(AccommodationImgDTO accommodationImgDTO);

}
