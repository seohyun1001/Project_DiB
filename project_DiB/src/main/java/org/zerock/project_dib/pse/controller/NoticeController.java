package org.zerock.project_dib.pse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.project_dib.pse.dto.NoticeDTO;
import org.zerock.project_dib.pse.dto.PageRequestDTO;
import org.zerock.project_dib.pse.dto.PageResponseDTO;
import org.zerock.project_dib.pse.service.NoticeService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("")
    public String getAllNotices(@ModelAttribute PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<NoticeDTO> responseDTO = noticeService.getAllNotices(pageRequestDTO);
        model.addAttribute("notices", responseDTO.getDtoList());
        model.addAttribute("pageRequestDTO", pageRequestDTO);
        model.addAttribute("totalPage", responseDTO.getTotalPage());
        return "notice/notice"; // 공지사항 리스트 페이지
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "notice/notice_register";
    }

    @PostMapping("/register")
    public String registerNotice(MultipartFile file, NoticeDTO noticeDTO) {
        noticeService.register(noticeDTO);
        return "redirect:/notice";
    }

    @GetMapping("/detail/{nno}")
    public String readNotice(@PathVariable Long nno, Model model) {
        NoticeDTO noticeDTO = noticeService.readOne(nno);
        model.addAttribute("notice", noticeDTO);
        return "notice/notice_detail"; // 공지사항 상세 페이지로 이동
    }

    @GetMapping("/modify/{nno}")
    public String showModifyForm(@PathVariable Long nno, Model model) {
        NoticeDTO noticeDTO = noticeService.readOne(nno);
        model.addAttribute("notice", noticeDTO);
        return "notice/notice_modify"; // 수정 페이지로 이동
    }

    @PostMapping("/modify")
    public String modifyNotice(@ModelAttribute NoticeDTO noticeDTO) {
        noticeDTO.setModdate(LocalDate.now()); // 수정일자를 현재 날짜로 설정
        noticeService.modify(noticeDTO);
        return "redirect:/notice";
    }

    @PostMapping("/delete/{nno}")
    public String deleteNotice(@PathVariable Long nno) {
        noticeService.remove(nno);
        return "redirect:/notice";
    }
}
