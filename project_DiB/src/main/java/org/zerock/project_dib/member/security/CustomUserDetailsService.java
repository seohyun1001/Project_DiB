package org.zerock.project_dib.member.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.project_dib.member.domain.Member;
import org.zerock.project_dib.mapper.MemberMapper;
import org.zerock.project_dib.member.security.dto.MemberSecurityDTO;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String mid) throws UsernameNotFoundException {

        log.info("Load User By mid" + mid);

        Optional<Member> result = memberMapper.getWithRoles(mid);

        if (result.isEmpty()) {
            throw new UsernameNotFoundException("User not found-----------------------------");
        }

        Member member = result.get();

        MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO(
                member.getMid(),
                member.getMpw(),
                member.getName(),
                member.getEmail(),
                member.getAddress(),
                member.getBirthdate(),
                member.getPhone(),
                member.getRegdate(),
                member.getModdate(),
                false,

                member.getRoleSet()
                        .stream()
                        .map(memberRole -> new SimpleGrantedAuthority("ROLE_" + memberRole
                                .name()))
                        .collect(Collectors.toSet())
        );

        log.info("memberSecurityDTO");
        log.info(memberSecurityDTO);

        return memberSecurityDTO;
    }
}
