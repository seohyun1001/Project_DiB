package org.zerock.project_dib.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.project_dib.member.service.MemberService;

@RestController
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;

    // 아이디 중복확인
    @GetMapping("/confirmId/{id}")
    public boolean confirmId(@PathVariable("id") String confirmId) {
        boolean exist = false;
        try{
            exist = memberService.confirmId(confirmId);
        }catch(Exception e){
            e.printStackTrace();
        }
        return exist;
    }
}
