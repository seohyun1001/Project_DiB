package org.zerock.project_dib.tourist.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "tourist")
@Data
public class TouristImg implements Comparable<TouristImg>{
    @Id
    private String uuid;

    private String fileName;

    private int tno;

    private int ord;

    @ManyToOne
    private Tourist tourist;

    @Override
    public int compareTo(TouristImg other) {
        return this.ord - other.ord;
    }
}
