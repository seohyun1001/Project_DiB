package org.zerock.project_dib.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.project_dib.restaurant.domain.RestaurantReply;
import org.zerock.project_dib.restaurant.dto.PageRequestDTO;
import org.zerock.project_dib.restaurant.dto.PageResponseDTO;
import org.zerock.project_dib.restaurant.dto.RestaurantReplyDTO;
import org.zerock.project_dib.mapper.RestaurantReplyMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantReplyServiceImpl implements RestaurantReplyService {

    private final RestaurantReplyMapper restaurantReplyMapper;

    @Override
    public int registerReply(RestaurantReplyDTO restaurantReplyDTO) {
        RestaurantReply restaurantReply = dtoToEntity(restaurantReplyDTO);
        restaurantReplyMapper.insertReview(restaurantReply);
        return restaurantReply.getReview_no();
    }

    @Override
    public void modifyReply(RestaurantReplyDTO restaurantReplyDTO) {
        RestaurantReply restaurantReply = dtoToEntity(restaurantReplyDTO);
        restaurantReplyMapper.updateReview(restaurantReply);
    }

    @Override
    public void deleteReply(int review_no) {
        restaurantReplyMapper.deleteReview(review_no);
    }

    @Override
    public PageResponseDTO<RestaurantReplyDTO> getListOfRestaurant(int rno, PageRequestDTO pageRequestDTO) {
        List<RestaurantReply> replies = restaurantReplyMapper.listOfRestaurant(rno, pageRequestDTO);
        List<RestaurantReplyDTO> dtoList = replies.stream().map(this::entityToDTO).collect(Collectors.toList());
        int total = replies.size();
        return new PageResponseDTO<>(pageRequestDTO, dtoList, total);
    }

    @Override
    public RestaurantReplyDTO readReply(int review_no) {
        RestaurantReply restaurantReply = restaurantReplyMapper.readReview(review_no);
        return entityToDTO(restaurantReply);
    }

    private RestaurantReply dtoToEntity(RestaurantReplyDTO dto) {
        return RestaurantReply.builder()
                .review_no(dto.getReview_no())
                .review_text(dto.getReview_text())
                .mid(dto.getMid())
                .review_like(dto.isReview_like())
                .rno(dto.getRno())
                .build();
    }

    private RestaurantReplyDTO entityToDTO(RestaurantReply entity) {
        return RestaurantReplyDTO.builder()
                .review_no(entity.getReview_no())
                .review_text(entity.getReview_text())
                .mid(entity.getMid())
                .review_like(entity.isReview_like())
                .rno(entity.getRno())
                .build();
    }
}
