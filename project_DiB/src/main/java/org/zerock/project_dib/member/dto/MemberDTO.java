package org.zerock.project_dib.member.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    @NotEmpty
    private String mid;

    @NotEmpty
    private String mwp;

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    private String address;
    private LocalDateTime birthdate;
    private String phone;
    private LocalDateTime regdate;
    private LocalDateTime moddate;

}
