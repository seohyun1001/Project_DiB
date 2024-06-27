package org.zerock.project_dib.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.project_dib.member.domain.Member;
import org.zerock.project_dib.member.dto.MemberDTO;
import org.zerock.project_dib.mapper.MemberMapper;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void join(MemberDTO memberDTO){
        log.info(modelMapper);
        Member member = modelMapper.map(memberDTO, Member.class);
        member.changePassword(passwordEncoder.encode(memberDTO.getMpw()));
//        member.addRole(MemberRole.USER);

        log.info("--------------------------------------");
        log.info(member);
//        log.info(member.getRoleSet());

        memberMapper.insert(member);
    }

    @Override
    public boolean confirmId(String id) throws MidExistException {
        int count = memberMapper.selectOneId(id);
        boolean exist = count > 0;
        System.out.println(exist);
//        if (exist) {
//            throw new MidExistException();
//        }
        return exist;
    }

    @Override
    public MemberDTO myPage(String mid) {
        Member member = memberMapper.selectOneMyPage(mid);
        MemberDTO memberDTO = modelMapper.map(member, MemberDTO.class);
        return memberDTO;
    }

    @Override
    public void modify(MemberDTO memberDTO) {
        Member member = modelMapper.map(memberDTO, Member.class);
        member.changePassword(passwordEncoder.encode(memberDTO.getMpw()));
        memberMapper.infoModify(member);
    }

    @Override
    public void delete(String mid) {
        memberMapper.deleteMember(mid);
    }


}
