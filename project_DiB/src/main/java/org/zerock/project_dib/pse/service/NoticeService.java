package org.zerock.project_dib.pse.service;

import org.springframework.web.multipart.MultipartFile;
import org.zerock.project_dib.pse.domain.Notice;
import org.zerock.project_dib.pse.dto.NoticeDTO;
import org.zerock.project_dib.pse.dto.PageRequestDTO;
import org.zerock.project_dib.pse.dto.PageResponseDTO;

import java.util.List;

public interface NoticeService {
    Long register(NoticeDTO noticeDTO);
    NoticeDTO readOne(Long nno);
    void modify(NoticeDTO noticeDTO);
    void remove(Long nno);

    List<NoticeDTO> getAllNotices();
    PageResponseDTO<NoticeDTO> search(PageRequestDTO pageRequestDTO);
}
