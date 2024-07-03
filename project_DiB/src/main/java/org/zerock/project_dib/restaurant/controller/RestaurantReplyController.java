package org.zerock.project_dib.restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.zerock.project_dib.restaurant.dto.PageRequestDTO;
import org.zerock.project_dib.restaurant.dto.PageResponseDTO;
import org.zerock.project_dib.restaurant.dto.RestaurantReplyDTO;
import org.zerock.project_dib.restaurant.service.RestaurantReplyService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/replies")
@RequiredArgsConstructor
@Log4j2
public class RestaurantReplyController {

    private final RestaurantReplyService restaurantReplyService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Integer> register(@Valid @RequestBody RestaurantReplyDTO restaurantReplyDTO, BindingResult bindingResult) throws BindException {

        log.info(restaurantReplyDTO);

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        int review_no = restaurantReplyService.registerReply(restaurantReplyDTO);
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("review_no", review_no);

        return resultMap;
    }

    @GetMapping(value = "/list/{rno}")
    @ResponseBody
    public PageResponseDTO<RestaurantReplyDTO> getList(@PathVariable("rno") int rno, PageRequestDTO pageRequestDTO) {
        PageResponseDTO<RestaurantReplyDTO> responseDTO = restaurantReplyService.getListOfRestaurant(rno, pageRequestDTO);
        return responseDTO;
    }

    @GetMapping(value = "/{review_no}")
    @ResponseBody
    public RestaurantReplyDTO getReplyDto(@PathVariable("review_no") int review_no) {
        RestaurantReplyDTO restaurantReplyDTO = restaurantReplyService.readReply(review_no);
        return restaurantReplyDTO;
    }

    @DeleteMapping(value = "/{review_no}")
    @ResponseBody
    public Map<String, Integer> remove(@PathVariable("review_no") int review_no) {
        restaurantReplyService.deleteReply(review_no);
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("review_no", review_no);
        return resultMap;
    }

    @PutMapping(value = "/{review_no}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Integer> modify(@PathVariable("review_no") int review_no, @RequestBody RestaurantReplyDTO restaurantReplyDTO) {
        restaurantReplyDTO.setReview_no(review_no);
        restaurantReplyService.modifyReply(restaurantReplyDTO);
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("review_no", review_no);
        return resultMap;
    }
}
