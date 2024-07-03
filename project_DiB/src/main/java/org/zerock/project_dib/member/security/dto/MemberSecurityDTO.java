package org.zerock.project_dib.member.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@ToString
public class MemberSecurityDTO extends User {

    private String mid;
    private String mpw;
    private String name;
    private String email;
    private String address;
    private String phone;
    private LocalDateTime regdate;
    private LocalDateTime moddate;
    private boolean social;

    public MemberSecurityDTO(String username,
                             String password,
                             String name,
                             String email,
                             String address,
                             String phone,
                             LocalDateTime regdate,
                             LocalDateTime moddate,
                             boolean social,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);

        this.mid = username;
        this.mpw = password;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.regdate = regdate;
        this.moddate = moddate;
        this.social = social;

    }
}
