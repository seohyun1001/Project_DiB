package org.zerock.project_dib.accommodation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.project_dib.accommodation.dto.AccommodationDTO;
import org.zerock.project_dib.accommodation.dto.AccommodationImgDTO;
import org.zerock.project_dib.accommodation.service.AccommodationService;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

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
    public String addAccommodation(MultipartFile file, @Valid AccommodationImgDTO accommodationImgDTO, @Valid AccommodationDTO accommodationDTO, RedirectAttributes redirectAttributes, BindingResult bindingResult) throws IOException, Exception {

        if (bindingResult.hasErrors()){
            log.info("has register error..........");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/accommodation/register";
        }

        String fileName = null;
        if (!file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            UUID uuid = UUID.randomUUID();
            fileName = uuid + "_" + originalFilename;
            file.transferTo(new File("c:\\upload\\" + fileName));
            accommodationImgDTO.setFile_name(fileName);
            accommodationImgDTO.setUuid(uuid.toString());
        }

        log.info(accommodationDTO + "\n---------------------------------------------------\n" + accommodationImgDTO);


        Long ano = accommodationService.insertAccommodation(accommodationDTO);
        accommodationImgDTO.setAno(ano);
        log.info(accommodationImgDTO);
        accommodationService.insertFile(accommodationImgDTO);

        return "redirect:/accommodation/list";
    }

    @GetMapping("/list")
    public void list(@Valid AccommodationDTO accommodationDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.info("has list error........................");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
        }

        model.addAttribute("accList", accommodationService.accList());
        model.addAttribute("allImages", accommodationService.findAllFiles());

    }

    @GetMapping({"/view", "/modify"})
    public void view(Long ano, Model model) {

        AccommodationDTO accommodationDTO = accommodationService.accInfo(ano);
        log.info(accommodationDTO);
        model.addAttribute("accInfo", accommodationDTO);

        List<AccommodationImgDTO> imgFileList = accommodationService.findAllFileByAno(ano);
        log.info(imgFileList);
        model.addAttribute("imageList", imgFileList);

    }

    @PostMapping("/modify")
    public String modify(MultipartFile file, Long ano, @Valid AccommodationImgDTO accommodationImgDTO, @Valid AccommodationDTO accommodationDTO, RedirectAttributes redirectAttributes, BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("has modify error........................");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/accommodation/modify?ano="+ ano;
        }

        String fileName = null;
        if (!file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            UUID uuid = UUID.randomUUID();
            fileName = uuid + "_" + originalFilename;
            file.transferTo(new File("c:\\upload\\" + fileName));
            accommodationImgDTO.setFile_name(fileName);
            accommodationImgDTO.setUuid(uuid.toString());
            accommodationImgDTO.setAno(ano);

            accommodationService.insertFile(accommodationImgDTO);
        }

        accommodationService.modify(accommodationDTO);

        return "redirect:/accommodation/view?ano=" + ano;
    }

    @PostMapping("/delete/{ano}")
    public void delete(Long ano, Model model) {

        List<AccommodationImgDTO> imgFileList = accommodationService.findAllFileByAno(ano);
        model.addAttribute("imageList", imgFileList);

        for (AccommodationImgDTO accommodationImgDTO : imgFileList) {
            String fileName = accommodationImgDTO.getFile_name();

            File fileToDelete = new File("c:\\upload\\" + fileName);

            if (fileToDelete.delete()) {
                System.out.println("파일 삭제 성공: " + fileToDelete.getName());
            } else {
                System.out.println("파일 삭제 실패: " + fileToDelete.getName());
            }
        }

        accommodationService.delete(ano);
        accommodationService.deleteFile(ano);

    }

}
