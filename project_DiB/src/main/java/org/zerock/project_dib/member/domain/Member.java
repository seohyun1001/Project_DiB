package org.zerock.project_dib.member.domain;

import lombok.*;

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
    private String mwp;
    private String name;
    private String email;
    private String address;
    private LocalDateTime birthdate;
    private String phone;
    private LocalDateTime regdate;
    private LocalDateTime moddate;

    private Set<MemberRole> roleSet = new HashSet<>();
}
