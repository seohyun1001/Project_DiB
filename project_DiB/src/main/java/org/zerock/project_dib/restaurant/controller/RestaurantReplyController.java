package org.zerock.project_dib.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.project_dib.restaurant.dto.PageRequestDTO;
import org.zerock.project_dib.restaurant.dto.PageResponseDTO;
import org.zerock.project_dib.restaurant.dto.RestaurantReplyDTO;
import org.zerock.project_dib.restaurant.service.RestaurantReplyService;
import org.zerock.project_dib.restaurant.service.RestaurantService;

import javax.validation.Valid;

@Controller
@RequestMapping("/replies")
@RequiredArgsConstructor
@Log4j2
public class RestaurantReplyController {

    private final RestaurantReplyService restaurantReplyService;
    private final RestaurantService restaurantService;

    @PostMapping("/addReply")
    public String addReply(@Valid RestaurantReplyDTO restaurantReplyDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Authentication authentication) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/restaurant/read?rno=" + restaurantReplyDTO.getRno();
        }

        // 현재 로그인된 사용자의 mid 설정
        String mid = authentication.getName();
        restaurantReplyDTO.setMid(mid);

        restaurantReplyService.registerReply(restaurantReplyDTO);
        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/restaurant/read?rno=" + restaurantReplyDTO.getRno();
    }

    @GetMapping("/list/{rno}")
    @ResponseBody
    public PageResponseDTO<RestaurantReplyDTO> getList(@PathVariable("rno") int rno, PageRequestDTO pageRequestDTO) {
        return restaurantReplyService.getListOfRestaurant(rno, pageRequestDTO);
    }

    @GetMapping("/{review_no}")
    @ResponseBody
    public RestaurantReplyDTO getReply(@PathVariable("review_no") int review_no) {
        return restaurantReplyService.readReply(review_no);
    }

    @PostMapping("/delete/{review_no}")
    public String deleteReply(@PathVariable("review_no") int review_no, @RequestParam("rno") int rno, RedirectAttributes redirectAttributes) {
        restaurantReplyService.deleteReply(review_no);
        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/restaurant/read?rno=" + rno;
    }

    @PostMapping("/update/{review_no}")
    public String updateReply(@PathVariable("review_no") int review_no,
                              @Valid RestaurantReplyDTO restaurantReplyDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/restaurant/read?rno=" + restaurantReplyDTO.getRno();
        }

        restaurantReplyDTO.setReview_no(review_no);
        restaurantReplyService.modifyReply(restaurantReplyDTO);
        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/restaurant/read?rno=" + restaurantReplyDTO.getRno();
    }
}
