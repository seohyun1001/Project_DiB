package org.zerock.project_dib.restaurant.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.zerock.project_dib.restaurant.dto.PageRequestDTO;
import org.zerock.project_dib.restaurant.dto.RestaurantReviewDTO;

import java.util.List;

@Mapper
public interface RestaurantMapper {
    List<RestaurantReviewDTO> selectAll();
    void insert(RestaurantReviewDTO restaurantReviewDTO);
    void delete(Long rno);
    void update(RestaurantReviewDTO restaurantReviewDTO);

    int getCount(PageRequestDTO pageRequestDTO);

    List<RestaurantReviewDTO> selectList(PageRequestDTO pageRequestDTO);

    RestaurantReviewDTO selectOne(Long rno);
}
