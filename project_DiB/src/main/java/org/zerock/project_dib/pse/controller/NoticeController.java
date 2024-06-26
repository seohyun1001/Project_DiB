package org.zerock.project_dib.pse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.project_dib.pse.dto.NoticeDTO;
import org.zerock.project_dib.pse.service.NoticeService;

import java.util.List;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("")
    public String getAllNotices(Model model) {
        model.addAttribute("notices", noticeService.getAllNotices());
        return "notice/notice"; // 공지사항 리스트 페이지
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "notice/register-notice";
    }

    @PostMapping("/register")
    public String registerNotice(@ModelAttribute NoticeDTO noticeDTO) {
        noticeService.register(noticeDTO);
        return "redirect:/notice";
    }

    @GetMapping("/{nno}")
    public String readNotice(@PathVariable Long nno, Model model) {
        NoticeDTO noticeDTO = noticeService.readOne(nno);
        model.addAttribute("notice", noticeDTO);
        return "notice_detail"; // 공지사항 상세 페이지로 이동
    }

    @PostMapping("/modify")
    public String modifyNotice(@ModelAttribute NoticeDTO noticeDTO) {
        noticeService.modify(noticeDTO);
        return "redirect:/notice";
    }

    @PostMapping("/delete/{nno}")
    public String deleteNotice(@PathVariable Long nno) {
        noticeService.remove(nno);
        return "redirect:/notice";
    }

}
