package org.zerock.project_dib.pse.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zerock.project_dib.pse.domain.Notice;
import org.zerock.project_dib.pse.dto.PageRequestDTO;

import java.util.List;

@Mapper
public interface NoticeMapper {
    void insert(Notice notice);
    Notice read(Long nno);
    void update(Notice notice);
    void delete(Long nno);
    List<Notice> getAll(@Param("pageRequestDTO") PageRequestDTO pageRequestDTO);
    int getCount();
}
