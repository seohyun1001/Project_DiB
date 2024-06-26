package org.zerock.project_dib.pse.service;

import org.zerock.project_dib.pse.dto.NoticeDTO;

import java.util.List;

public interface NoticeService {
    Long register(NoticeDTO noticeDTO);
    NoticeDTO readOne(Long nno);
    void modify(NoticeDTO noticeDTO);
    void remove(Long nno);
    List<NoticeDTO> getAllNotices();
}
