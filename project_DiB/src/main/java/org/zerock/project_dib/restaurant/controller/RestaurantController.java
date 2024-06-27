package org.zerock.project_dib.restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.project_dib.restaurant.dto.RestaurantDTO;
import org.zerock.project_dib.restaurant.dto.uploadfile.UploadFileDTO;
import org.zerock.project_dib.restaurant.dto.uploadfile.UploadResultDTO;
import org.zerock.project_dib.restaurant.service.RestaurantService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {
    @Value("${org.zerock.upload.path}")
    private String uploadPath;
    private final RestaurantService restaurantService;

    @GetMapping("/register")
    public String registerGET(Model model) {
        // GET 요청에 대한 처리 로직
        return null;
//        return "register_form"; // register_form.html과 같은 뷰 파일을 반환
    }

    @PostMapping("/register")
    public String registerPOST(@Valid RestaurantDTO restaurantDTO
            , BindingResult bindingResult
            , RedirectAttributes redirectAttributes) {
        log.info("rest Post register.......");
        if (bindingResult.hasErrors()) {
            log.info("has errors.......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/restaurant/register";
        }
        log.info(restaurantDTO);
        int rno = restaurantService.insertRestaurant(restaurantDTO);
        redirectAttributes.addFlashAttribute("result", rno);
        return "redirect:/restaurant/list"; // 등록 후 리스트 페이지로 리다이렉트
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@ModelAttribute UploadFileDTO uploadFileDTO,
                         RedirectAttributes redirectAttributes) {
        log.info(uploadFileDTO);
        if (uploadFileDTO.getFiles() != null) {
            List<UploadResultDTO> list = new ArrayList<>();
            uploadFileDTO.getFiles().forEach(multipartFile -> {
                String originalName = multipartFile.getOriginalFilename();
                log.info(originalName);
                String uuid = UUID.randomUUID().toString();
                Path savePath = Paths.get(uploadPath, uuid + "_" + originalName);
                boolean image = false;
                try {
                    multipartFile.transferTo(savePath);
                    if (Files.probeContentType(savePath).startsWith("image")) {
                        image = true;
                        File thumbFile = new File(uploadPath, "s_" + uuid + "_" + originalName);
                        Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200, 200);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                list.add(UploadResultDTO.builder()
                        .uuid(uuid)
                        .fileName(originalName)
                        .img(image)
                        .build());
            });

            // 업로드된 파일 정보를 데이터베이스에 저장
            restaurantService.saveUploadFiles(list);

            redirectAttributes.addFlashAttribute("uploadResults", list); // 업로드 결과를 Flash 속성에 추가
        }

        return "redirect:/restaurant/register"; // 업로드 후 등록 페이지로 리다이렉트
    }

    @GetMapping("/list")
    public String getAllRestaurants(Model model, RedirectAttributes redirectAttributes) {
        try {
            List<RestaurantDTO> dtoList = restaurantService.getAllRestaurants();
            model.addAttribute("list", dtoList);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/restaurant/list";
        }
        return "/restaurant/list";
    }
}
