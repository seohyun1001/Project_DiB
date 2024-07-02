package org.zerock.project_dib.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.zerock.project_dib.notice.domain.Notice;
import java.util.List;

@Mapper
public interface NoticeMapper {
    void insert(Notice notice);
    Notice read(Long nno);
    void update(Notice notice);
    void delete(Long nno);
    List<Notice> getAll();
}
