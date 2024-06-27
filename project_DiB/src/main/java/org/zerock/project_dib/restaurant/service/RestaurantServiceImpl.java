package org.zerock.project_dib.restaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.zerock.project_dib.restaurant.domain.Restaurant;
import org.zerock.project_dib.restaurant.domain.RestaurantImage;
import org.zerock.project_dib.restaurant.dto.PageRequestDTO;
import org.zerock.project_dib.restaurant.dto.PageResponseDTO;
import org.zerock.project_dib.restaurant.dto.RestaurantDTO;
import org.zerock.project_dib.restaurant.dto.uploadfile.UploadResultDTO;
import org.zerock.project_dib.restaurant.mapper.RestaurantImageMapper;
import org.zerock.project_dib.restaurant.mapper.RestaurantMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final ModelMapper modelMapper;
    private final RestaurantMapper restaurantMapper;
    private final RestaurantImageMapper restaurantImageMapper;
    private final String uploadPath = "C:\\upload";

    @Override
    public int insertRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = dtoToEntity(restaurantDTO);
        restaurantMapper.insertRest(restaurant);

        if (restaurantDTO.getFileNames() != null) {
            restaurantDTO.getFileNames().forEach(fileName -> {
                String[] arr = fileName.split("_", 2);
                restaurant.addImage(arr[0], arr[1]);
            });
            for (RestaurantImage image : restaurant.getImageSet()) {
                restaurantImageMapper.insertFile(image);
            }
        }
        return restaurant.getRno();
    }

    @Override
    public void saveUploadFiles(List<UploadResultDTO> uploadResultDTOList) {
        if (uploadResultDTOList != null && !uploadResultDTOList.isEmpty()) {
            uploadResultDTOList.forEach(uploadResultDTO -> {
                RestaurantImage restaurantImage = RestaurantImage.builder()
                        .uuid(uploadResultDTO.getUuid())
                        .file_name(uploadResultDTO.getFileName())
                        .build();

                restaurantImageMapper.insertFile(restaurantImage);
            });
        }
    }

    @Override
    public List<RestaurantDTO> getAllRestaurants() {
        return restaurantMapper.selectAll().stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantDTO getOne(int rno) {
        Restaurant restaurant = restaurantMapper.readOne(rno);
        return entityToDto(restaurant);
    }

    @Override
    public void update(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = modelMapper.map(restaurantDTO, Restaurant.class);
        restaurantMapper.update(restaurant);
    }

    @Override
    public void delete(int rno){restaurantMapper.delete(rno);}

    @Override
    public void deleteFile(int rno){restaurantImageMapper.deleteFile(rno);}

    @Override
    public PageResponseDTO<RestaurantDTO> search(PageRequestDTO pageRequestDTO) {
        List<Restaurant> restaurants = restaurantMapper.search(pageRequestDTO);
        List<RestaurantDTO> dtoList = restaurants.stream()
                .map(restaurant -> {
                    RestaurantDTO restaurantDTO = modelMapper.map(restaurant, RestaurantDTO.class);
                    restaurantDTO.setFileNames(restaurant.getImageSet().stream()
                            .map(image -> image.getUuid() + "_" + image.getFile_name())
                            .collect(Collectors.toList()));
                    return restaurantDTO;
                })
                .collect(Collectors.toList());

        int total = restaurants.size();
        return PageResponseDTO.<RestaurantDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }

    private Restaurant dtoToEntity(RestaurantDTO restaurantDTO) {
        return Restaurant.builder()
                .rno(restaurantDTO.getRno())
                .rest_name(restaurantDTO.getRest_name())
                .rest_exp(restaurantDTO.getRest_exp())
                .rest_exp2(restaurantDTO.getRest_exp2())
                .rest_loc(restaurantDTO.getRest_loc())
                .rest_phone(restaurantDTO.getRest_phone())
                .rest_menu(restaurantDTO.getRest_menu())
                .rest_time(restaurantDTO.getRest_time())
                .build();
    }

    private RestaurantDTO entityToDto(Restaurant restaurant) {
        return RestaurantDTO.builder()
                .rno(restaurant.getRno())
                .rest_name(restaurant.getRest_name())
                .rest_exp(restaurant.getRest_exp())
                .rest_exp2(restaurant.getRest_exp2())
                .rest_loc(restaurant.getRest_loc())
                .rest_phone(restaurant.getRest_phone())
                .rest_menu(restaurant.getRest_menu())
                .rest_time(restaurant.getRest_time())
                .fileNames(restaurant.getImageSet().stream()
                        .map(image -> image.getUuid() + "_" + image.getFile_name())
                        .collect(Collectors.toList()))
                .build();
    }



}
