package org.zerock.project_dib.tourist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.project_dib.tourist.dto.PageRequestDTO;
import org.zerock.project_dib.tourist.dto.PageResponseDTO;
import org.zerock.project_dib.tourist.dto.TouristDTO;
import org.zerock.project_dib.tourist.dto.TouristImgDTO;
import org.zerock.project_dib.tourist.dto.upload.UploadFileDTO;
import org.zerock.project_dib.tourist.service.TouristService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tourist")
@Log4j2
public class TouristController {

    private final TouristService touristService;

    @GetMapping("/list")
    public String getList(Model model,
                          @RequestParam(value = "page", defaultValue = "1") int page,
                          @RequestParam(value = "size", defaultValue = "15") int size,
                          @RequestParam(value = "filter", required = false) String filter,
                          @RequestParam(value = "keyword", required = false) String keyword) {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(page)
                .size(size)
                .filter(filter)
                .keyword(keyword).
                build();
        pageRequestDTO.setFilterAndKeyword();

        PageResponseDTO<TouristDTO> list = touristService.search(pageRequestDTO);

        log.info("--------------- tourist ---------------" + list);

        model.addAttribute("list", list);
        return "/tourist/list";
    }

    @GetMapping("/read/{tno}")
    public String read(@PathVariable("tno") int tno, Model model) {
        List<String> fileNames = touristService.getImgList(tno);
        TouristDTO dto = touristService.read(tno);
        dto.setFileNames(fileNames);
        model.addAttribute("dto", dto);
        return "/tourist/read";
    }

    @GetMapping("/register")
    public void registerGET() {
    }

    @PostMapping("/register")
    public String register(TouristDTO touristDTO, @RequestParam("file") MultipartFile file) throws IOException {
        int tno = touristService.register(touristDTO);
        touristDTO.setTno(tno);
        if (!file.isEmpty()) {
            touristService.registerImg(touristDTO, file);
        }

        return "redirect:/tourist/list";
    }

    @PostMapping("/remove/{tno}")
    public String remove(@PathVariable("tno") int tno) {
        touristService.remove(tno);
        return "redirect:/tourist/list";
    }

    @PostMapping("/modify/{tno}")
    @ResponseBody
    public void modify(@PathVariable("tno") int tno, @RequestBody TouristDTO touristDTO) {
        touristDTO.setTno(tno);
        touristService.modify(touristDTO);
    }

//    @GetMapping("/{tno}/images")
//    public List<TouristImgDTO> getImgList(@PathVariable("tno") int tno, Model model) {
//        List<TouristImgDTO> fileNames = touristService.getImgList(tno);
//        model.addAttribute("fileNames", fileNames);
//        return touristService.getImgList(tno);
//    }

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


}