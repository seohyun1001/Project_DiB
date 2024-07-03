package org.zerock.project_dib.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.zerock.project_dib.member.domain.Member;

import java.util.Optional;

@Mapper
public interface MemberMapper {

    // 회원가입
    void insert(Member member);

    // 회원가입시 아이디 중복 확인
    int selectOneId(String mid);

    //
    Optional<Member> getWithRoles(String mid);

    // 마이페이지 들어가기
    Member selectOneMyPage(String mid);

    // 정보 수정
    void infoModify(Member member);

    // 회원 삭제 및 탈퇴
    void deleteMember(String mid);

}
