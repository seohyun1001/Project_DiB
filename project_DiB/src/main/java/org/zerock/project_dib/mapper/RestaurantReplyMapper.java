package org.zerock.project_dib.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zerock.project_dib.restaurant.domain.Reply;
import org.zerock.project_dib.restaurant.dto.PageRequestDTO;

import java.util.List;

@Mapper
public interface RestaurantReplyMapper {

    void insertReview(Reply reply);

    void updateReview(Reply reply);

    void deleteReview(int review_no);

    List<Reply> listOfRestaurant(@Param("rno") Long rno, @Param("pageRequestDTO") PageRequestDTO pageRequestDTO);

    Reply readReview(int review_no);
}
