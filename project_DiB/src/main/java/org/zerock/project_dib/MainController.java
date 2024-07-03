package org.zerock.project_dib;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String index() {
        return "/index";
    }

    @GetMapping("/board/read")
    public String read() {
        return "board/read";
    }

}
