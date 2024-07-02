package org.zerock.project_dib.tourist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.project_dib.tourist.dto.TouristDTO;
import org.zerock.project_dib.tourist.dto.TouristImgDTO;
import org.zerock.project_dib.tourist.dto.upload.UploadFileDTO;
import org.zerock.project_dib.tourist.service.TouristService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tourists")
public class TouristController {

    private final TouristService touristService;

    @GetMapping("/")
    public String getList(Model model) {
        List<TouristDTO> list = touristService.getList();
        model.addAttribute("list", list);
        return "tourists";
    }

    @GetMapping("/read/{tno}")
    @ResponseBody
    public TouristDTO read(@PathVariable("tno") int tno) {
        return touristService.read(tno);
    }



    @PostMapping("/register")
    public String register(TouristDTO touristDTO, @RequestParam("file") MultipartFile file) throws IOException {
        int tno = touristService.register(touristDTO);
        touristDTO.setTno(tno);
        if (!file.isEmpty()) {
            touristService.registerImg(touristDTO, file);
        }

        return "redirect:/tourists/";
    }



    @PostMapping("/modify/{tno}")
    @ResponseBody
    public void modify(@PathVariable("tno") int tno, @RequestBody TouristDTO touristDTO) {
        touristDTO.setTno(tno);
        touristService.modify(touristDTO);
    }

    @GetMapping("/{tno}/images")
    @ResponseBody
    public List<TouristImgDTO> getImgList(@PathVariable("tno") int tno) {
        return touristService.getImgList(tno);
    }

    @PostMapping("/{tno}/images")
    @ResponseBody
    public void registerImg(@PathVariable("tno") int tno, @RequestParam MultipartFile file) throws IOException {
        TouristDTO touristDTO = new TouristDTO();
        touristDTO.setTno(tno);

        if (!file.isEmpty()) {
            var upDownDto = new UploadFileDTO();
            var imgList = new ArrayList<MultipartFile>();
            imgList.add(file);
            upDownDto.setFiles(imgList);

            var updownController = new UpDownController();
            updownController.upload(upDownDto);
        }

        touristService.registerImg(touristDTO, file);
    }

    @PostMapping("/remove/{tno}/images")
    @ResponseBody
    public void removeImgs(@PathVariable("tno") int tno) {
        touristService.removeImgs(tno);
    }
    @GetMapping("/list")
    public String list(){
        return "tourist/list";
    }


}