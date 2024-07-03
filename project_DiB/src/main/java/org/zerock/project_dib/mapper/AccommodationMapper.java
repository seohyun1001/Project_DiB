package org.zerock.project_dib.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.zerock.project_dib.accommodation.domain.AccommodationImgVO;
import org.zerock.project_dib.accommodation.domain.AccommodationVO;

import java.util.List;

@Mapper
public interface AccommodationMapper {

    void insertAccommodation(AccommodationVO accommodationVO);

    List<AccommodationVO> findAll();

    AccommodationVO selectOne(Long ano);

    void update(AccommodationVO accommodationVO);

    void delete(Long ano);

    void insertFile(AccommodationImgVO accommodationImgVO);

    List<AccommodationImgVO> findAllFilesByAno(Long ano);

    AccommodationImgVO findAllFilesByOrd(int ord);

    List<AccommodationImgVO> findAllFiles();

    void deleteFile(int ord);

}
