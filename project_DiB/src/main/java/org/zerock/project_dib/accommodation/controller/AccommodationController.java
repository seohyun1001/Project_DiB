package org.zerock.project_dib.accommodation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.project_dib.accommodation.dto.AccommodationDTO;
import org.zerock.project_dib.accommodation.service.AccommodationService;

@Controller
@RequestMapping("/accommodation")
@Log4j2
@RequiredArgsConstructor
public class AccommodationController {

    private final AccommodationService accommodationService;

    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String addAccommodation(@Valid AccommodationDTO accommodationDTO, RedirectAttributes redirectAttributes, BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()){
            log.info("has error..........");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/accommodation/register";
        }
        log.info(accommodationDTO);

        accommodationService.insertAccommodation(accommodationDTO);

        return "redirect:/accommodation/list";
    }

    @GetMapping("/list")
    public void list() {

    }

}
