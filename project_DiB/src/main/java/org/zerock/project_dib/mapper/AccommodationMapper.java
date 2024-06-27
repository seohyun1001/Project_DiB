package org.zerock.project_dib.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.zerock.project_dib.accommodation.domain.AccommodationVO;

@Mapper
public interface AccommodationMapper {

    void insertAccommodation(AccommodationVO accommodationVO);

}
