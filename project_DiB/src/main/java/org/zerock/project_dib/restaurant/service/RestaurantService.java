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
    PageResponseDTO<RestaurantDTO> search(PageRequestDTO pageRequestDTO);
    void updateImages(int rno, List<UploadResultDTO> uploadResults);

    //최신 3개의 게시글을 가져오는 메서드_성언 추가
    List<RestaurantDTO> getLatestThree();
}

