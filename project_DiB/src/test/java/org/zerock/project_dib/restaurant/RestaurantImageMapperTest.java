package org.zerock.project_dib.restaurant;

//import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.project_dib.restaurant.domain.RestaurantImage;
import org.zerock.project_dib.mapper.RestaurantImageMapper;

import java.util.UUID;

@SpringBootTest
//@Log4j2
public class RestaurantImageMapperTest {
    @Autowired
    private RestaurantImageMapper restaurantImageMapper;

    @Test
    public void testInsertImage() {
        // 이미지의 UUID를 생성
        String uuid = UUID.randomUUID().toString();

        // 샘플 RestaurantImage 객체 생성
        RestaurantImage restaurantImage = RestaurantImage.builder()
                .uuid(uuid)
                .fileName("sample_image.jpg")
                .rno(3) // 테스트를 위해 rno를 1로 가정
                .ord(1)
                .build();

        // 이미지 데이터를 데이터베이스에 삽입
        restaurantImageMapper.insertFile(restaurantImage);

        // 결과를 로그로 출력
//        log.info("Inserted RestaurantImage: " + restaurantImage);
    }


}
