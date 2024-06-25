package org.zerock.project_dib.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.project_dib.member.dto.MemberDTO;
import org.zerock.project_dib.member.service.MemberService;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public String joinPost(MemberDTO memberDTO, RedirectAttributes redirectAttributes) {
        log.info("-------------------------- join --------------------------");
        log.info("memberDTO : " + memberDTO);

        memberService.join(memberDTO);

        redirectAttributes.addFlashAttribute("joinResult", "success");
        return "redirect:/member/login";
    }

    @GetMapping("/join")
    public String join() {
        return "MemberJoin";
    }

//    @PostMapping("/confirm")
//    public String confirmPost(String id, RedirectAttributes redirectAttributes) {
//        log.info("-------------------------- confirm --------------------------");
//        try {
//            memberService.confirmId(id);
//        } catch (MidExistException e) {
//            redirectAttributes.addFlashAttribute("error", "midExist");
//            return "redirect:/member/join";
//        }
//        redirectAttributes.addFlashAttribute("confirmResult", "success");
//        return "redirect:/member/join";
//    }

}
