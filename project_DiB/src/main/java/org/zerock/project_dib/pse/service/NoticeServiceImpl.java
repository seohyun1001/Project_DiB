package org.zerock.project_dib.pse.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.zerock.project_dib.pse.domain.Notice;
import org.zerock.project_dib.pse.dto.NoticeDTO;
import org.zerock.project_dib.pse.mapper.NoticeMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeMapper noticeMapper;
    private final ModelMapper modelMapper;

    @Override
    public Long register(NoticeDTO noticeDTO) {
        Notice notice = modelMapper.map(noticeDTO, Notice.class);
        notice.setRegdate(LocalDate.now());
        notice.setModdate(LocalDate.now());
        noticeMapper.insert(notice);
        return notice.getNno();
    }

    @Override
    public NoticeDTO readOne(Long nno) {
        Notice notice = noticeMapper.read(nno);
        return modelMapper.map(notice, NoticeDTO.class);
    }

    @Override
    public void modify(NoticeDTO noticeDTO) {
        Notice notice = modelMapper.map(noticeDTO, Notice.class);
        noticeMapper.update(notice);
    }

    @Override
    public void remove(Long nno) {
        noticeMapper.delete(nno);
    }

    @Override
    public List<NoticeDTO> getAllNotices() {
        List<Notice> notices = noticeMapper.getAll();
        return notices.stream()
                .map(notice -> modelMapper.map(notice, NoticeDTO.class))
                .collect(Collectors.toList());
    }
}
