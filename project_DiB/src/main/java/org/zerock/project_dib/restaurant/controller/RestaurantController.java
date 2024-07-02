package org.zerock.project_dib.restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.project_dib.restaurant.dto.RestaurantReviewDTO;
import org.zerock.project_dib.restaurant.service.RestaurantService;

import java.security.Principal;

@Controller
@RequestMapping("/restaurant")
@Log4j2
@RequiredArgsConstructor

public class RestaurantController {
    private final RestaurantService restaurantService;
    @GetMapping("/goodfood")
    public void goodfood(Model model, Long rno){
        RestaurantReviewDTO restaurantReviewDTO=restaurantService.getOne(rno);
        model.addAttribute("dto",restaurantReviewDTO);

    }
    @GetMapping("/register/{rno}")
    public void registerRead(@PathVariable ("rno") Long rno, Principal principal,Model model){
        ;
        model.addAttribute("rno",rno);
        model.addAttribute("mid",principal.getName());
    }
    @PostMapping("/register")
    public String registerPost(@Valid RestaurantReviewDTO restaurantReviewDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        Long rno=restaurantService.register(restaurantReviewDTO);
        redirectAttributes.addFlashAttribute("rno",rno);
        return "redirect:/restaurant/goodfood";
    }

}
