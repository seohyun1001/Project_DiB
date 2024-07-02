package org.zerock.project_dib.tourist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TouristPageController {

    @GetMapping("/tourist/register")
    public String showRegisterPage() {
        return "tourist/register"; // "tourist/register.html" 파일을 의미
    }
}