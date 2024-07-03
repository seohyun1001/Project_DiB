package org.zerock.project_dib.notice.service;

<<<<<<< HEAD:project_DiB/src/main/java/org/zerock/project_dib/notice/service/NoticeService.java
import org.zerock.project_dib.notice.dto.NoticeDTO;
=======
import org.springframework.web.multipart.MultipartFile;
import org.zerock.project_dib.pse.domain.Notice;
import org.zerock.project_dib.pse.dto.NoticeDTO;
import org.zerock.project_dib.pse.dto.PageRequestDTO;
import org.zerock.project_dib.pse.dto.PageResponseDTO;
>>>>>>> origin/장규원:project_DiB/src/main/java/org/zerock/project_dib/pse/service/NoticeService.java

import java.util.List;

public interface NoticeService {
    Long register(NoticeDTO noticeDTO);
    NoticeDTO readOne(Long nno);
    void modify(NoticeDTO noticeDTO);
    void remove(Long nno);

    List<NoticeDTO> getAllNotices();
    PageResponseDTO<NoticeDTO> search(PageRequestDTO pageRequestDTO);
}
