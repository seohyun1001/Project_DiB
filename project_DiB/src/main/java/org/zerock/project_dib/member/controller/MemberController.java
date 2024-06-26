package org.zerock.project_dib.member.controller;

import jakarta.validation.Valid;
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
    public String joinPost(@Valid MemberDTO memberDTO, RedirectAttributes redirectAttributes) {
        log.info("-------------------------- join --------------------------");
        log.info("memberDTO : " + memberDTO);

        memberService.join(memberDTO);

        redirectAttributes.addFlashAttribute("joinResult", "success");
        return "redirect:/member/login";
    }

    @GetMapping("/join")
    public String join() {
        return "/member/join";
    }

    @GetMapping("/login")
    public void login(String error, String logout) {
        log.info("------------------------- login -------------------------");
        log.info("-------------------- error : " + error + "--------------------");
        if (logout != null){
            log.info("user logout --------------");
        }
    }

}
