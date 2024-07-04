package org.zerock.project_dib.tourist.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString(exclude = "imageSet")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tourist extends BaseEntity{

    @Id
    private int tno;

    private String tourName;

    private String tourAddr;

    private String tourOpentime;

    private String tourClosetime;

    private boolean tourParking;

    private String tourContent;

    private LocalDateTime modDate;

    private LocalDateTime regDate;

    @OneToMany(mappedBy = "tourist", orphanRemoval = true)
    @Builder.Default
    private Set<TouristImg> imageSet = new HashSet<>();
}