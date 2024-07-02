package org.zerock.project_dib.restaurant.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.zerock.project_dib.restaurant.domain.Restaurant;
import org.zerock.project_dib.restaurant.dto.PageRequestDTO;

import java.util.List;

@Mapper
public interface RestaurantMapper {
    @Options(useGeneratedKeys = true, keyProperty = "rno")
    int insertRest(Restaurant restaurant);
    List<Restaurant> selectAll();
    Restaurant readOne(int rno);
    void update(Restaurant restaurant);
    void delete(int rno);
    List<Restaurant> search(@Param("pageRequestDTO") PageRequestDTO pageRequestDTO);
    int countTotal(@Param("pageRequestDTO") PageRequestDTO pageRequestDTO); // 추가된 메서드
}
