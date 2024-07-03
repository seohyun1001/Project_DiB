package org.zerock.project_dib.member.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@ToString(exclude = "roleSet")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {

    private String mid;
    private String mpw;
    private String name;
    private String email;
    private String address;
    private String phone;
    private LocalDateTime regdate;
    private LocalDateTime moddate;
    private boolean social;
    private String roleSet;

//    @Builder.Default
//    private final Set<MemberRole> roleSet = new HashSet<>();

    public void changePassword(String mpw) {
        this.mpw = mpw;
    }

//    public void addRole(MemberRole role) {
//        this.roleSet.add(role);
//    }
}
