package org.zerock.project_dib.pse.service;

import org.zerock.project_dib.pse.dto.One2oneDTO;
import org.zerock.project_dib.pse.dto.PageRequestDTO;
import org.zerock.project_dib.pse.dto.PageResponseDTO;

import java.util.List;

public interface One2oneService {
    Long register(One2oneDTO one2oneDTO);
    One2oneDTO readOne(Long otono);
    void modify(One2oneDTO one2oneDTO);
    void remove(Long otono);

    List<One2oneDTO> getAllOne2ones();
    PageResponseDTO<One2oneDTO> search(PageRequestDTO pageRequestDTO);
}
