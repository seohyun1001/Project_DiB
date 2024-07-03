package org.zerock.project_dib.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zerock.project_dib.restaurant.domain.RestaurantReply;
import org.zerock.project_dib.restaurant.dto.PageRequestDTO;

import java.util.List;

@Mapper
public interface RestaurantReplyMapper {

    void insertReview(RestaurantReply restaurantReply);

    void updateReview(RestaurantReply restaurantReply);

    void deleteReview(int review_no);

    int countTotalReviews(int rno);

    List<RestaurantReply> listOfRestaurant(@Param("rno") int rno, @Param("pageRequestDTO") PageRequestDTO pageRequestDTO);

    RestaurantReply readReview(int review_no);
}
