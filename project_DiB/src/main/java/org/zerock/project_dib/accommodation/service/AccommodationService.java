package org.zerock.project_dib.accommodation.service;

import org.zerock.project_dib.accommodation.dto.AccommodationDTO;
import org.zerock.project_dib.accommodation.dto.AccommodationImgDTO;
import org.zerock.project_dib.accommodation.dto.PageRequestDTO;
import org.zerock.project_dib.accommodation.dto.PageResponseDTO;

import java.util.List;

public interface AccommodationService {

    Long insertAccommodation(AccommodationDTO accommodationDTO);

    List<AccommodationDTO> accList();

    PageResponseDTO<AccommodationDTO> search(PageRequestDTO pageRequestDTO);

    AccommodationDTO accInfo(Long ano);

    void modify(AccommodationDTO accommodationDTO);

    void remove(Long ano);

    void insertFile(AccommodationImgDTO accommodationImgDTO);

    List<AccommodationImgDTO> findAllFileByAno(Long ano);

    AccommodationImgDTO findAllFileByOrd();

    AccommodationImgDTO findFileByUuid(String uuid);

    List<AccommodationImgDTO> findAllFiles();

    void removeFile(String uuid);

    AccommodationImgDTO findOrdByAno(Long ano);

}

