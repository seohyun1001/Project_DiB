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

    List<Notice> selectAll();
    List<Notice> search(@Param("pageRequestDTO") PageRequestDTO pageRequestDTO);

//    // 페이징된 공지사항 목록 조회
//    List<Notice> getAll(@Param("pageRequestDTO") PageRequestDTO pageRequestDTO);
//
//    // 페이징된 공지사항 총 개수 조회
//    int getCount(@Param("pageRequestDTO") PageRequestDTO pageRequestDTO);
}
