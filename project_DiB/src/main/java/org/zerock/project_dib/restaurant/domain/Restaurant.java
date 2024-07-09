package org.zerock.project_dib.restaurant.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ToString(exclude = "imageSet")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant extends BaseEntity {
    @Id
    private int rno;
    private String rest_name;
    private String rest_exp;
    private String rest_exp2;
    private String rest_loc;
    private String rest_phone;
    private String rest_menu;
    private String rest_time;

    @OneToMany(mappedBy = "restaurant", orphanRemoval = true)
    @Builder.Default
    private List<RestaurantImage> imageSet  = new ArrayList<>();

    public void addImage(String uuid, String fileName) {
        RestaurantImage restaurantImage = RestaurantImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .rno(this.rno)
                .ord(imageSet.size())
                .build();
        imageSet.add(restaurantImage);
    }
    public void clearImages() {
        this.imageSet.clear();
    }
}
