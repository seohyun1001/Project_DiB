package org.zerock.project_dib.restaurant.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.zerock.project_dib.restaurant.domain.Restaurant;

import java.util.List;

@Mapper
public interface RestaurantMapper {
    int insertRest(Restaurant restaurant);
    List<Restaurant> selectAll();
    Restaurant readOne(int rno);
    void update(Restaurant restaurant);
    void delete(int rno);

}
