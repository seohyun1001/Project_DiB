package org.zerock.project_dib.restaurant.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.zerock.project_dib.restaurant.dto.RestaurantDTO;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "restaurant")
public class RestaurantImage implements Comparable<RestaurantImage> {
    @Id
    private String uuid;
    private String file_name;
    private int rno;
    private int ord;

    @ManyToOne
    private Restaurant restaurant;

    @Override
    public int compareTo(RestaurantImage other) {
        return this.ord - other.ord;
    }
}


