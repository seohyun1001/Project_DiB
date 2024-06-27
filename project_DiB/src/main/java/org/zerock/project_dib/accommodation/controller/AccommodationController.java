package org.zerock.project_dib.accommodation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
            log.info("has register error..........");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/accommodation/register";
        }
        log.info(accommodationDTO);

        accommodationService.insertAccommodation(accommodationDTO);

        return "redirect:/accommodation/list";
    }

    @GetMapping("/list")
    public void list(@Valid AccommodationDTO accommodationDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.info("has list error........................");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
        }

        model.addAttribute("accList", accommodationService.accList());

    }

    @GetMapping({"/view", "/modify"})
    public void view(int ano, Model model) {

        AccommodationDTO accommodationDTO = accommodationService.accInfo(ano);
        log.info(accommodationDTO);
        model.addAttribute("accInfo", accommodationDTO);

    }

    @PostMapping("/modify")
    public String modify(int ano, @Valid AccommodationDTO accommodationDTO, RedirectAttributes redirectAttributes, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("has modify error........................");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/accommodation/modify?ano="+ ano;
        }

        log.info(accommodationDTO);
        

        return "redirect:/accommodation/view?ano=" + ano;
    }


}
