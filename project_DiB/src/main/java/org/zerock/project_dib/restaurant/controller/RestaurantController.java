package org.zerock.project_dib.restaurant.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/restaurant")
@Log4j2
@RequiredArgsConstructor

public class RestaurantController {
    @GetMapping("/readSample")
    public void sample(){
        log.info("test");
        //return "redirect:/restaurant/readSample";
    }

}
