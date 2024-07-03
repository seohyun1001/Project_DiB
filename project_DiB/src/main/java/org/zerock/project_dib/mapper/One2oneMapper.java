package org.zerock.project_dib.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zerock.project_dib.pse.domain.One2one;
import org.zerock.project_dib.pse.dto.PageRequestDTO;

import java.util.List;

@Mapper
public interface One2oneMapper {
    void insert(One2one one2one);
    One2one read(Long otono);
    void update(One2one one2one);
    void delete(Long otono);
    int totalCount(@Param("pageRequestDTO") PageRequestDTO pageRequestDTO);

    List<One2one> selectAll();
    List<One2one> search(@Param("pageRequestDTO") PageRequestDTO pageRequestDTO);

}
