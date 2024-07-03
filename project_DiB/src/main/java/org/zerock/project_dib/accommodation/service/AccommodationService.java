package org.zerock.project_dib.accommodation.service;

import org.zerock.project_dib.accommodation.dto.AccommodationDTO;
import org.zerock.project_dib.accommodation.dto.AccommodationImgDTO;

import java.util.List;

public interface AccommodationService {

    Long insertAccommodation(AccommodationDTO accommodationDTO);

    List<AccommodationDTO> accList();

    AccommodationDTO accInfo(Long ano);

    void modify(AccommodationDTO accommodationDTO);

    void remove(Long ano);

    void insertFile(AccommodationImgDTO accommodationImgDTO);

    List<AccommodationImgDTO> findAllFileByAno(Long ano);

    AccommodationImgDTO findAllFileByOrd(int ord);

    List<AccommodationImgDTO> findAllFiles();

    void removeFile(int ord);

}
