package org.zerock.project_dib.restaurant.service;

import org.zerock.project_dib.restaurant.domain.Reply;
import org.zerock.project_dib.restaurant.dto.PageRequestDTO;
import org.zerock.project_dib.restaurant.dto.PageResponseDTO;

public interface ReplyService {

    Long registerReply(Reply reply);

    void modifyReply(Reply reply);

    void removeReply(Long review_no);

    PageResponseDTO<Reply> getListOfRestaurantReply(Long rno, PageRequestDTO pageRequestDTO);

    Reply readReply(Long review_no);
}
