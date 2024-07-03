package org.zerock.project_dib.member.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
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
    private String mpw;

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    private String address;

    private String phone;
    private LocalDateTime regdate;
    private LocalDateTime moddate;

}
