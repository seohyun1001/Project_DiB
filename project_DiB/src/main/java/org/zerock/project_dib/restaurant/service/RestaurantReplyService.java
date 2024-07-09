package org.zerock.project_dib.restaurant.service;

import org.zerock.project_dib.restaurant.dto.PageRequestDTO;
import org.zerock.project_dib.restaurant.dto.PageResponseDTO;
import org.zerock.project_dib.restaurant.dto.RestaurantReplyDTO;


public interface RestaurantReplyService {
    int registerReply(RestaurantReplyDTO restaurantReplyDTO);
    void modifyReply(RestaurantReplyDTO restaurantReplyDTO);
    void deleteReply(int review_no);
    PageResponseDTO<RestaurantReplyDTO> getListOfRestaurant(int rno, PageRequestDTO pageRequestDTO);
    RestaurantReplyDTO readReply(int review_no);
}
