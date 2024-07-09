//package org.zerock.project_dib.restaurant;
//
//
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.zerock.project_dib.restaurant.domain.Restaurant;
//import org.zerock.project_dib.restaurant.domain.RestaurantImage;
//import org.zerock.project_dib.restaurant.dto.PageRequestDTO;
//import org.zerock.project_dib.restaurant.dto.PageResponseDTO;
//import org.zerock.project_dib.restaurant.dto.RestaurantDTO;
//import org.zerock.project_dib.mapper.RestaurantImageMapper;
//import org.zerock.project_dib.mapper.RestaurantMapper;
//import org.zerock.project_dib.restaurant.service.RestaurantService;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//
//public class RestaurantMapperTest {
//    @Autowired
//    private ModelMapper modelMapper;
//    @Autowired
//    private RestaurantMapper restaurantMapper;
//    @Autowired
//    private RestaurantService restaurantService;
//    @Autowired
//    private RestaurantImageMapper restaurantImageMapper;
//
//
//    @Test
//    public void testInsert() {
//        IntStream.rangeClosed(1,100).forEach(i -> {
//            Restaurant restaurant = Restaurant.builder()
//                    .rno(i)
//                    .rest_name("rest_name" + i)
//                    .rest_exp("rest_exp" + i)
//                    .rest_exp2("rest_exp2" + i)
//                    .rest_loc("rest_loc" + i)
//                    .rest_phone("rest_phone" + i)
//                    .rest_menu("rest_menu" + i)
//                    .rest_time("rest_time" + i)
//                    .build();
//            restaurantMapper.insertRest(restaurant);
//            log.info("Inserted restaurant with rno: " + restaurant.getRno());
//        });
//    }
//
//    @Test
//    public void testSelectAll() {
//
//        List<RestaurantDTO> restaurantDTOs = restaurantMapper.selectAll().stream()
//                .map(restaurant -> {
//                    RestaurantDTO restaurantDTO = modelMapper.map(restaurant, RestaurantDTO.class);
//                    restaurantDTO.setFileNames(restaurant.getImageSet().stream()
//                            .map(image -> image.getUuid() + "_" + image.getFileName())
//                            .collect(Collectors.toList()));
//                    return restaurantDTO;
//                })
//                .collect(Collectors.toList());
//        restaurantDTOs.forEach(dto -> log.info("Restaurant with rno {}: {}", dto.getRno(), dto));
//    }
//
//
//    @Test
//    public void testReadOne() {
//
//        int rno = 3;
//        Restaurant restaurant = restaurantMapper.readOne(rno);
//        if (restaurant != null) {
//            RestaurantDTO restaurantDTO = modelMapper.map(restaurant, RestaurantDTO.class);
//            restaurantDTO.setFileNames(restaurant.getImageSet().stream()
//                    .map(image -> image.getUuid() + "_" + image.getFileName())
//                    .collect(Collectors.toList()));
//            log.info("Restaurant with rno {}: {}", rno, restaurantDTO);
//        } else {
//            log.info("Restaurant with rno {} not found", rno);
//        }
//    }
//
//    @Test
//    public void testReadOne2() {
//
//        int rno = 1;
//        Restaurant restaurant = restaurantMapper.readOne(rno);
//        List<RestaurantImage> restaurantImage = restaurantImageMapper.selectFileByRNO(rno);
//        log.info("rno: ");
//    }
//
//    @Test
//    public void testModify() {
//        int rno = 79;
//
//        RestaurantDTO modifiedDTO = RestaurantDTO.builder()
//                .rno(rno)
//                .rest_name("고기집")
//                .rest_exp("고기를")
//                .rest_exp2("팝니다")
//                .rest_loc("개금동")
//                .rest_phone("123-456-789")
//                .rest_menu("고기")
//                .rest_time("7~10")
//                .build();
//        restaurantService.update(modifiedDTO);
//
//        RestaurantDTO updatedDTO = restaurantService.getOne(rno);
//        log.info("업데이트된 레스토랑 DTO: {}", updatedDTO);
//    }
//
//    @Test
//    public void testDelete() {
//        int rno = 6;
//        RestaurantDTO restaurantDTO = restaurantService.getOne(rno);
//        if (restaurantDTO != null) {
//            log.info("삭제 전 레스토랑 정보: {}", restaurantDTO);
//        } else {
//            log.info("삭제할 레스토랑이 존재하지 않습니다. rno: {}", rno);
//            return;
//        }
//        restaurantService.delete(rno);
//    }
//
//    @Test
//    public void testSearchRestaurants() {
//        log.info("Testing searchRestaurants() method...");
//
//        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
//                .page(1)
//                .size(10)
//                .rno(65)
//                .rest_name(null)
//                .rest_loc(null)
//                .build();
//
//        PageResponseDTO<RestaurantDTO> responseDTO = restaurantService.search(pageRequestDTO);
//
//        assertThat(responseDTO).isNotNull();
//        assertThat(responseDTO.getDtoList()).isNotEmpty();
//
//        responseDTO.getDtoList().forEach(restaurantDTO -> {
//            log.info("Restaurant: {}", restaurantDTO);
//        });
//    }
//}
//
