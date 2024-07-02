package org.zerock.project_dib.restaurant.domain;

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
@ToString(exclude = "restaurant")
public class RestaurantImage implements Comparable<RestaurantImage> {
    @Id
    private String uuid;
    private String fileName;

    private int rno;
    private int ord;

    @ManyToOne
    private Restaurant restaurant;

    @Override
    public int compareTo(RestaurantImage other) {
        return this.ord - other.ord;
    }

    public void changeRestaurant(Restaurant restaurant){
        this.restaurant = restaurant;
    }
}
