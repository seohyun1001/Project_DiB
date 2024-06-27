package org.zerock.project_dib.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.zerock.project_dib.accommodation.domain.AccommodationVO;

import java.util.List;

@Mapper
public interface AccommodationMapper {

    void insertAccommodation(AccommodationVO accommodationVO);

    List<AccommodationVO> findAll();

    AccommodationVO selectOne(int ano);

}
