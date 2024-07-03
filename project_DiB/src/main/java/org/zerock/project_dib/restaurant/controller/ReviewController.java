package org.zerock.project_dib.restaurant.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.project_dib.restaurant.dto.PageRequestDTO;
import org.zerock.project_dib.restaurant.dto.RestaurantDTO;
import org.zerock.project_dib.restaurant.service.RestaurantService;

@Controller
@RequestMapping("/restreview")
@Log4j2
@RequiredArgsConstructor

public class ReviewController {
    private final RestaurantService restaurantService;
//    @GetMapping("/list")
//    public void list(){
//
//    }

//    @GetMapping("/goodfood/{a}")
//    public void read(@PathVariable("a") Long rno, Model model){
////        model.addAttribute("restaurant",restaurantService.getOne(rno));
//        model.addAttribute("review",restaurantService.getOne(rno));
//
//    }

    @GetMapping("/register")
    public void review(){
    }
    @PostMapping("/register")
    public String registerPost(PageRequestDTO pageRequestDTO, BindingResult bindingResult, RestaurantDTO restaurantDTO, RedirectAttributes redirectAttributes){
        String link = pageRequestDTO.getLink();
        redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
        redirectAttributes.addAttribute("rno", restaurantDTO.getRno());
        return "redirect:/restaurant/read?"+link;
    }
//
//    @GetMapping("/read")
//    public void read(){
//
//    }


}
