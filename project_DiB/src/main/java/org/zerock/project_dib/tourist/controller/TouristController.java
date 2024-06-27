package org.zerock.project_dib.tourist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.project_dib.tourist.domain.Tourist;
import org.zerock.project_dib.tourist.domain.TouristImg;
import org.zerock.project_dib.tourist.service.TouristService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tourists")
public class TouristController {

    private final TouristService touristService;

    @GetMapping("/")
    public String getList(Model model) {
        model.addAttribute("list", touristService.getList());
        return "tourists";
    }

    @GetMapping("/read/{tno}")
    public Tourist read(@PathVariable("tno") int tno) {
        return touristService.read(tno);
    }

    @PostMapping("/register")
    public String register(Tourist tourist) {
        touristService.register(tourist);
        return "redirect:/tourists/";
    }

    @PostMapping("/modify/{tno}")
    public void modify(@PathVariable("tno") int tno, Tourist tourist) {
        tourist.setTno(tno);
        touristService.modify(tourist); // 이 부분이 추가됨
    }

    @GetMapping("/{tno}/images")
    public List<TouristImg> getImgList(@PathVariable("tno") int tno) {
        return touristService.getImgList(tno);
    }

    @PostMapping("/{tno}/images")
    public void registerImg(@PathVariable("tno") int tno, TouristImg touristImg) {
        touristImg.setTno(tno);
        touristService.registerImg(touristImg);
    }

    @PostMapping("/remove/{tno}/images")
    public void removeImgs(@PathVariable("tno") int tno) {
        touristService.removeImgs(tno);
    }
}