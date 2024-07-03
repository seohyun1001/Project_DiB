package org.zerock.project_dib.pse.service;

import org.zerock.project_dib.pse.dto.FaqDTO;
import org.zerock.project_dib.pse.dto.PageRequestDTO;
import org.zerock.project_dib.pse.dto.PageResponseDTO;

import java.util.List;

public interface FaqService {
    Long register(FaqDTO faqDTO);
    FaqDTO readOne(Long fno);
    void modify(FaqDTO faqDTO);
    void remove(Long fno);

    List<FaqDTO> getAllFaqs();
    PageResponseDTO<FaqDTO> search(PageRequestDTO pageRequestDTO);
}
