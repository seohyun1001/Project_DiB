package org.zerock.project_dib.member.service;

import org.zerock.project_dib.member.dto.MemberDTO;

public interface MemberService {
    static class MidExistException extends Exception {}

    void join(MemberDTO memberDTO);

    boolean confirmId(boolean exist) throws MidExistException;
}
