package org.zerock.project_dib.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zerock.project_dib.pse.domain.Faq;
import org.zerock.project_dib.pse.dto.PageRequestDTO;

import java.util.List;

@Mapper
public interface FaqMapper {
    void insert(Faq faq);
    Faq read(Long fno);
    void update(Faq faq);
    void delete(Long fno);
    int totalCount(@Param("pageRequestDTO") PageRequestDTO pageRequestDTO);

    List<Faq> selectAll();
    List<Faq> search(@Param("pageRequestDTO") PageRequestDTO pageRequestDTO);

}
