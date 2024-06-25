package org.zerock.project_dib.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.zerock.project_dib.member.domain.Member;

import java.util.Optional;

@Mapper
public interface MemberMapper {

    void insert(Member member);

    int selectOneId(String mid);

    Optional<Member> getWithRoles(String mid);

}
