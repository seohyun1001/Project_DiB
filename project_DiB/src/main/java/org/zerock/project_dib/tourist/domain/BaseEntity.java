package org.zerock.project_dib.tourist.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BaseEntity {
    private LocalDateTime regdate;
    private LocalDateTime moddate;
}
