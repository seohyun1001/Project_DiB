package org.zerock.project_dib.restaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.project_dib.mapper.RestaurantReplyMapper;
import org.zerock.project_dib.restaurant.domain.Reply;
import org.zerock.project_dib.restaurant.dto.PageRequestDTO;
import org.zerock.project_dib.restaurant.dto.PageResponseDTO;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final RestaurantReplyMapper restaurantReplyMapper;

    @Override
    public Long registerReply(Reply reply) {
        restaurantReplyMapper.insertReview(reply);
        return reply.getReview_no();
    }

    @Override
    public void modifyReply(Reply reply) {
        restaurantReplyMapper.updateReview(reply);
    }

    @Override
    public void removeReply(Long review_no) {
        restaurantReplyMapper.deleteReview(review_no.intValue());
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<Reply> getListOfRestaurantReply(Long rno, PageRequestDTO pageRequestDTO) {
        List<Reply> replies = restaurantReplyMapper.listOfRestaurant(rno, pageRequestDTO);
        int total = replies.size(); // 적절한 total 계산 필요
        return PageResponseDTO.<Reply>withAll()
                .dtoList(replies)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Reply readReply(Long review_no) {
        return restaurantReplyMapper.readReview(review_no.intValue());
    }
}
