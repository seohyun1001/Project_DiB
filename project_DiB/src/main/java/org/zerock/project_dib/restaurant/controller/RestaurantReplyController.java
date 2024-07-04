package org.zerock.project_dib.restaurant.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, PageResponseDTO> getList(@PathVariable("rno") int rno, PageRequestDTO pageRequestDTO, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> as = (List<GrantedAuthority>) authentication.getAuthorities();
        String name = principal.getName();
        for(GrantedAuthority ga : as) {
            if(ga.getAuthority().equals("ROLE_ADMIN")) {
                name = "ADMIN";
            }
        }
        return Map.of(name, restaurantReplyService.getListOfRestaurant(rno, pageRequestDTO));
    }

    @GetMapping("/{review_no}")
    @ResponseBody
    public RestaurantReplyDTO getReply(@PathVariable("review_no") int review_no) {
        return restaurantReplyService.readReply(review_no);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or principal.username == #replyDTO.mid")
    @PostMapping("/delete")
    public String deleteReply(RestaurantReplyDTO replyDTO, Authentication authentication, RedirectAttributes redirectAttributes) {
        RestaurantReplyDTO restaurantReplyDTO = restaurantReplyService.readReply(replyDTO.getReview_no());
        if (restaurantReplyDTO != null) {
            restaurantReplyService.deleteReply(replyDTO.getReview_no());
            redirectAttributes.addFlashAttribute("result", "success");
        } else {
            log.error("Reply not found: " + replyDTO.getReview_no());
            redirectAttributes.addFlashAttribute("error", "Reply not found");
        }
        return "redirect:/restaurant/read?rno=" + (restaurantReplyDTO != null ? restaurantReplyDTO.getRno() : "");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or principal.username == #replyDTO.mid")
    @PostMapping("/update")
    public String updateReply(RestaurantReplyDTO replyDTO,
                              Authentication authentication,
                              RedirectAttributes redirectAttributes) {

        RestaurantReplyDTO restaurantReplyDTO = restaurantReplyService.readReply(replyDTO.getReview_no());
        if (restaurantReplyDTO != null) {
            restaurantReplyDTO.setReview_text(replyDTO.getReview_text());
            restaurantReplyService.modifyReply(restaurantReplyDTO);
            redirectAttributes.addFlashAttribute("result", "success");
        } else {
            log.error("Reply not found: " + replyDTO.getReview_no());
            redirectAttributes.addFlashAttribute("error", "Reply not found");
        }
        return "redirect:/restaurant/read?rno=" + (restaurantReplyDTO != null ? restaurantReplyDTO.getRno() : "");
    }
}
