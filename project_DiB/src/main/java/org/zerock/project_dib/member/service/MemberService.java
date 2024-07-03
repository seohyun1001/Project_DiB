package org.zerock.project_dib.member.service;

import org.zerock.project_dib.member.domain.Member;
import org.zerock.project_dib.member.dto.MemberDTO;

public interface MemberService {
    static class MidExistException extends Exception {}

    void join(MemberDTO memberDTO);

    boolean confirmId(String id) throws MidExistException ;

    MemberDTO myPage(String mid);

    void modify(MemberDTO memberDTO);

    void delete(String mid);
}
