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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.project_dib.restaurant.dto.PageRequestDTO;
import org.zerock.project_dib.restaurant.dto.PageResponseDTO;
import org.zerock.project_dib.restaurant.dto.RestaurantDTO;
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
@RequiredArgsConstructor
@RequestMapping("/restaurant")
@Log4j2
public class RestaurantController {

    @Value("${org.zerock.upload.path}")
    private String uploadPath;
    private final RestaurantService restaurantService;

    @GetMapping("/register")
    public String registerGET(Model model) {
        return "restaurant/register";
    }

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String registerPOST(@Valid RestaurantDTO restaurantDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               @RequestParam("files") MultipartFile[] files) {
        log.info("rest Post register.......");
        if (bindingResult.hasErrors()) {
            log.info("has errors.......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/restaurant/register";
        }
        log.info(restaurantDTO);

        // 1. 레스토랑 등록
        int rno = restaurantService.insertRestaurant(restaurantDTO);
        log.info("Inserted Restaurant RNO: " + rno);

        // 2. 업로드된 파일 처리
        List<UploadResultDTO> uploadResults = new ArrayList<>();
        boolean isFirstImage = true;  // 첫 번째 이미지인지 여부를 추적하기 위한 플래그
        for (MultipartFile multipartFile : files) {
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
            uploadResults.add(UploadResultDTO.builder()
                    .uuid(uuid)
                    .fileName(originalName)
                    .img(image)
                    .build());
        }

        // 3. 파일 정보 저장
        restaurantService.saveUploadFiles(uploadResults, rno);

        redirectAttributes.addFlashAttribute("result", rno);
        return "redirect:/restaurant/list"; // 등록 후 리스트 페이지로 리다이렉트
    }

    @GetMapping("/list")
    public String getAllRestaurants(@RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "size", defaultValue = "10") int size,
                                    @RequestParam(value = "filter", required = false) String filter,
                                    @RequestParam(value = "keyword", required = false) String keyword,
                                    Model model) {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(page)
                .size(size)
                .filter(filter)
                .keyword(keyword)
                .build();
        pageRequestDTO.setFilterAndKeyword();

        PageResponseDTO<RestaurantDTO> responseDTO = restaurantService.search(pageRequestDTO);

        model.addAttribute("result", responseDTO);
        return "restaurant/list";
    }


}
