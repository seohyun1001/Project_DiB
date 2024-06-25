package org.zerock.project_dib.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.oauth2.login.OAuth2LoginSecurityMarker;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.project_dib.member.domain.Member;
import org.zerock.project_dib.member.dto.MemberDTO;
import org.zerock.project_dib.member.service.MemberService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class MemberServiceTests {

    @Autowired
    private MemberService memberService;

    // join test
    @Test
    public void joinTest() {
        MemberDTO memberDTO = MemberDTO.builder()
                .mid("test")
                .mpw("1234")
                .name("tester")
                .email("test@test.com")
                .phone("01000000000")
                .build();
        memberService.join(memberDTO);
    }


}
