package org.zerock.project_dib.member.dto;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    private String mid;
    private String mwp;
    private String name;
    private String email;
    private String address;
    private LocalDateTime birthdate;
    private String phone;
    private LocalDateTime regdate;
    private LocalDateTime moddate;

}
