package org.zerock.project_dib.restaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.project_dib.restaurant.dto.PageRequestDTO;
import org.zerock.project_dib.restaurant.dto.PageResponseDTO;
import org.zerock.project_dib.restaurant.dto.RestaurantReviewDTO;
import org.zerock.project_dib.restaurant.mappers.RestaurantMapper;
import org.modelmapper.ModelMapper;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService{
    private final RestaurantMapper restaurantMapper;
    private final ModelMapper modelMapper;
    @Override
    public List<RestaurantReviewDTO> getAll() {
        List<RestaurantReviewDTO> dtoList=restaurantMapper.selectAll();
        return dtoList;
    }

    @Override
    public void register(RestaurantReviewDTO restaurantReviewDTO) {

        restaurantMapper.insert(restaurantReviewDTO);
    }

    @Override
    public void remove(Long rno) {
        restaurantMapper.delete(rno);
    }

    @Override
    public void modify(RestaurantReviewDTO restaurantReviewDTO) {
        restaurantMapper.update(restaurantReviewDTO);
    }

    @Override
    public PageResponseDTO<RestaurantReviewDTO> getList(PageRequestDTO pageRequestDTO) {
        List<RestaurantReviewDTO> dtoList=restaurantMapper.selectList(pageRequestDTO);
        int total=restaurantMapper.getCount(pageRequestDTO);
        PageResponseDTO<RestaurantReviewDTO> pageResponseDTO=PageResponseDTO.<RestaurantReviewDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
        return pageResponseDTO;
    }

    @Override
    public RestaurantReviewDTO getOne(Long rno) {

        return modelMapper.map(restaurantMapper.selectOne(rno),RestaurantReviewDTO.class);
    }
}
