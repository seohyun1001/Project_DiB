package org.zerock.project_dib.restaurant.service;

import org.springframework.stereotype.Service;

import org.zerock.project_dib.restaurant.dto.PageRequestDTO;
import org.zerock.project_dib.restaurant.dto.PageResponseDTO;
import org.zerock.project_dib.restaurant.dto.RestaurantDTO;
import org.zerock.project_dib.restaurant.dto.uploadfile.UploadResultDTO;

import java.util.List;

@Service
public interface RestaurantService {
    int insertRestaurant(RestaurantDTO restaurantDTO);
    void saveUploadFiles(List<UploadResultDTO> uploadResultDTOList,int rno);
    List<RestaurantDTO> getAllRestaurants();
    RestaurantDTO getOne(int rno);
    void update(RestaurantDTO restaurantDTO);
    void delete(int rno);
    void deleteFile(int rno);
    PageResponseDTO<RestaurantDTO> search(PageRequestDTO pageRequestDTO);



//    // restaurantDTO를 restaurant 객체로 바꾸기위해 사용하는 메서드
//    default Restaurant dtoToEntity(RestaurantDTO restaurantDTO) {
//        Restaurant restaurant = Restaurant.builder()
//                .rno(restaurantDTO.getRno())
//                .rest_name(restaurantDTO.getRest_name())
//                .rest_exp(restaurantDTO.getRest_exp())
//                .rest_exp2(restaurantDTO.getRest_exp2())
//                .rest_loc(restaurantDTO.getRest_loc())
//                .rest_phone(restaurantDTO.getRest_phone())
//                .rest_menu(restaurantDTO.getRest_menu())
//                .rest_time(restaurantDTO.getRest_time())
//                .build();
//        // restaurantDTO의 String[] 타입을 Set<RestaurantImage>타입으로 바꾸는 if문
//        if(restaurantDTO.getFileNames() != null){
//            // 반복문으로 String[]의 문자열을 하나씩 변경
//            restaurantDTO.getFileNames().forEach(fileName ->{
//                // uuid_파일이름.확장자 -> arr[0] = uuid, arr[1] = 파일이름.확장자
//                String[] arr = fileName.split("_",2);
//                restaurant.addImage(arr[0], arr[1]);
//            });
//        }
//        return restaurant;
//    }
//    //restaurant를 restaurantDTO로 변환하기위한 메서드
//    default RestaurantDTO entityToDto(Restaurant restaurant) {
//        //단순 데이터 설정
//        RestaurantDTO restaurantDTO = RestaurantDTO.builder()
//                .rno(restaurant.getRno())
//                .rest_name(restaurant.getRest_name())
//                .rest_exp(restaurant.getRest_exp())
//                .rest_exp2(restaurant.getRest_exp2())
//                .rest_loc(restaurant.getRest_loc())
//                .rest_phone(restaurant.getRest_phone())
//                .rest_menu(restaurant.getRest_menu())
//                .rest_time(restaurant.getRest_time())
//                .regdate(restaurant.getRegdate())
//                .moddate(restaurant.getModdate())
//                .build();
//        //Set<restaurantImage>을  List<String>로 변환하기위한 코드
//        List<String> fileNames = restaurant.getImageSet().stream().sorted()
//                // uuid_파일명.확장자 형식으로 restaurantImage데이터를 String 타입 변환
//                .map(restaurantImage -> restaurantImage.getUuid()+"_"+restaurantImage.getFileName())
//                //변환한 결과값을 합쳐서 List타입으로 반환
//                .collect(Collectors.toList());
//        restaurantDTO.setFileNames(fileNames);
//        return restaurantDTO;
//    }
}

