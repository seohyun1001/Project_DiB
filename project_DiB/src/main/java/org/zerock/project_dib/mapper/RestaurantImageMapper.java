package org.zerock.project_dib.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.zerock.project_dib.restaurant.domain.RestaurantImage;

import java.util.List;

@Mapper
public interface RestaurantImageMapper {
    int insertFile(RestaurantImage uploadResultDTO);
    List<RestaurantImage> selectFileByRNO(int rno);
    void deleteFile(int rno);
}
