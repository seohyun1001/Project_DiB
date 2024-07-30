package org.zerock.project_dib.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zerock.project_dib.accommodation.domain.AccommodationImgVO;
import org.zerock.project_dib.accommodation.domain.AccommodationVO;
import org.zerock.project_dib.accommodation.dto.AccommodationImgDTO;
import org.zerock.project_dib.accommodation.dto.PageRequestDTO;

import java.util.List;

@Mapper
public interface AccommodationMapper {

    void insertAccommodation(AccommodationVO accommodationVO);

    List<AccommodationVO> findAll();

    List<AccommodationVO> search(@Param("pageRequestDTO") PageRequestDTO pageRequestDTO);

    int getTotalCount(@Param("pageRequestDTO") PageRequestDTO pageRequestDTO);

    AccommodationVO selectOne(Long ano);

    void update(AccommodationVO accommodationVO);

    void delete(Long ano);

    void insertFile(AccommodationImgVO accommodationImgVO);

    List<AccommodationImgVO> findAllFilesByAno(Long ano);

    AccommodationImgVO findFileByFirstOrd();

    AccommodationImgVO findFileByUuid(String uuid);

    List<AccommodationImgVO> findAllFiles();

    void deleteFile(String uuid);

    AccommodationImgVO findOrdByAno(Long ano);

}
