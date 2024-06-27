package org.zerock.project_dib.restaurant.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zerock.project_dib.restaurant.domain.Restaurant;
import org.zerock.project_dib.restaurant.dto.PageRequestDTO;

import java.util.List;

@Mapper
public interface RestaurantMapper {
    int insertRest(Restaurant restaurant);
    List<Restaurant> selectAll();
    Restaurant readOne(int rno);
    void update(Restaurant restaurant);
    void delete(int rno);
    List<Restaurant> search(@Param("pageRequestDTO") PageRequestDTO pageRequestDTO);
}
