package org.zerock.project_dib.restaurant.service;

import org.zerock.project_dib.restaurant.dto.PageRequestDTO;
import org.zerock.project_dib.restaurant.dto.PageResponseDTO;
import org.zerock.project_dib.restaurant.dto.RestaurantReviewDTO;

import java.util.List;

public interface RestaurantService {
    List<RestaurantReviewDTO> getAll();
    void register(RestaurantReviewDTO restaurantReviewDTO);
    void remove(Long rno);
    void modify(RestaurantReviewDTO restaurantReviewDTO);
    PageResponseDTO<RestaurantReviewDTO> getList(PageRequestDTO pageRequestDTO);
    RestaurantReviewDTO getOne(Long rno);
}
