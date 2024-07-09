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
import org.zerock.project_dib.accommodation.dto.*;
import org.zerock.project_dib.accommodation.service.AccommodationService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/accommodation")
@Log4j2
@RequiredArgsConstructor
public class AccommodationController {

    private final AccommodationService accommodationService;

    @GetMapping("/register")
    public void register() {

    }

    @PostMapping("/register")
    public String addAccommodation(AccFileDTO files, @Valid AccommodationImgDTO accommodationImgDTO, @Valid AccommodationDTO accommodationDTO) throws IOException, Exception {

        Long ano = accommodationService.insertAccommodation(accommodationDTO);

        String fileName = null;
        int i = 0;
        for(MultipartFile file : files.getFiles()) {
            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                UUID uuid = UUID.randomUUID();
                fileName = uuid + "_" + originalFilename;
                file.transferTo(new File("c:\\upload\\" + fileName));
                accommodationImgDTO.setFile_name(fileName);
                accommodationImgDTO.setUuid(uuid.toString());
                accommodationImgDTO.setAno(ano);
                accommodationImgDTO.setOrd(i);
                i++;
                accommodationService.insertFile(accommodationImgDTO);
            }
        }

        accommodationImgDTO.setAno(ano);
        log.info(accommodationImgDTO);

        return "redirect:/accommodation/list";
    }

    @GetMapping("/list")
    public void list(@RequestParam(value = "page", defaultValue = "1") int page,
                     @RequestParam(value = "size", defaultValue = "10") int size,
                     Model model) {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(page)
                .size(size)
                .build();

        PageResponseDTO<AccommodationDTO> responseDTO = accommodationService.search(pageRequestDTO);

        List<Long> anoList = responseDTO.getDtoList().stream()
                .map(AccommodationDTO::getAno)
                .collect(Collectors.toList());

        List<AccommodationImgDTO> firtImageList = new ArrayList<>();

        for (Long ano : anoList) {
            Optional<AccommodationImgDTO> firstImageOptional = accommodationService.findAllFileByAno(ano).stream().findFirst();
            firstImageOptional.ifPresent(firtImageList::add);
        }

        model.addAttribute("accList", responseDTO.getDtoList());
        model.addAttribute("totalPage", responseDTO.getTotalPage());
        model.addAttribute("pageRequestDTO", pageRequestDTO);
        model.addAttribute("allImages", firtImageList);
    }

    @GetMapping({"/view", "/modify"})
    public void view(Long ano, Model model, PageRequestDTO pageRequestDTO) {

        AccommodationDTO accommodationDTO = accommodationService.accInfo(ano);
        log.info(accommodationDTO);
        model.addAttribute("accInfo", accommodationDTO);

        List<AccommodationImgDTO> imgFileList = accommodationService.findAllFileByAno(ano);
        log.info(imgFileList);
        model.addAttribute("imageList", imgFileList);

    }

    @PostMapping("/modify")
    public String modify(AccFileDTO files, Long ano, @Valid AccommodationImgDTO accommodationImgDTO, @Valid AccommodationDTO accommodationDTO, PageRequestDTO pageRequestDTO) throws IOException {

        AccommodationImgDTO fileList = accommodationService.findOrdByAno(ano);

        String fileName = null;
        int i = (fileList != null) ? fileList.getOrd() + 1 : 0;
        for(MultipartFile file : files.getFiles()) {
            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                UUID uuid = UUID.randomUUID();
                fileName = uuid + "_" + originalFilename;
                file.transferTo(new File("c:\\upload\\" + fileName));
                accommodationImgDTO.setFile_name(fileName);
                accommodationImgDTO.setUuid(uuid.toString());
                accommodationImgDTO.setOrd(i);
                i++;
                accommodationService.insertFile(accommodationImgDTO);
            }
        }

        accommodationService.modify(accommodationDTO);

        return "redirect:/accommodation/view?ano=" + ano + "&" + pageRequestDTO.getLink();
    }

    @PostMapping("/delete/{ano}")
    public void delete(@PathVariable Long ano) {

        try {
            List<AccommodationImgDTO> imgList = accommodationService.findAllFileByAno(ano);

            for (AccommodationImgDTO accommodationImgDTO : imgList) {

                String fileName = accommodationImgDTO.getFile_name();

                File fileToDelete = new File("c:\\upload\\" + fileName);

                if (fileToDelete.delete()) {
                    System.out.println("파일 삭제 성공: " + fileToDelete.getName());
                } else {
                    System.out.println("파일 삭제 실패: " + fileToDelete.getName());
                }
            }

            accommodationService.remove(ano);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
