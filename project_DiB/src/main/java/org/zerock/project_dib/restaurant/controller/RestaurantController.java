package org.zerock.project_dib.restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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
        if (files != null && files.length > 0 && !files[0].isEmpty()) {
            List<UploadResultDTO> uploadResults = new ArrayList<>();
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
        }
        return "redirect:/restaurant/list";
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

        //최신 3개의 게시글 가져오기_성언 추가
        List<RestaurantDTO> latestThree = restaurantService.getLatestThree();

        model.addAttribute("result", responseDTO);

        //성언 추가
        model.addAttribute("latestThree", latestThree);
        
        return "restaurant/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping({"/read", "/modify"})
    public void modifyRead(int rno, PageRequestDTO pageRequestDTO, Model model) {
        RestaurantDTO restaurantDTO = restaurantService.getOne(rno);
        log.info(restaurantDTO);

        // 원본 이미지 경로를 생성하여 DTO에 설정
        List<String> originalFileNames = new ArrayList<>();
        for (String fileName : restaurantDTO.getFileNames()) {
            // 섬네일 경로에서 원본 경로로 변환 (예: "s_"로 시작하는 부분 제거)
            String originalFileName = fileName.startsWith("s_") ? fileName.substring(2) : fileName;
            originalFileNames.add(originalFileName);
        }
        restaurantDTO.setFileNames(originalFileNames);

        model.addAttribute("dto", restaurantDTO);
    }



//    @PreAuthorize("principal.username == #restaurantDTO.rest_name")
//    @PostMapping("/modify")
//    public String modify(PageRequestDTO pageRequestDTO,
//                         @Valid RestaurantDTO restaurantDTO,
//                         BindingResult bindingResult,
//                         RedirectAttributes redirectAttributes) {
//        log.info("restaurant Modify register.......");
//        if (bindingResult.hasErrors()) {
//            log.info("has errors.......");
//            String link = pageRequestDTO.getLink();
//            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
//            redirectAttributes.addAttribute("rno", restaurantDTO.getRno());
//            return "redirect:/restaurant/modify?" + link;
//        }
//        System.out.println(restaurantDTO.getRno());
//        restaurantService.update(restaurantDTO);
//        redirectAttributes.addFlashAttribute("result", "modified");
//        redirectAttributes.addAttribute("rno", restaurantDTO.getRno());
//        return "redirect:/restaurant/list";
//    }

    @PreAuthorize("principal.username == #restaurantDTO.rest_name")
    @PostMapping(value = "/modify", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String modify(PageRequestDTO pageRequestDTO,
                         @Valid RestaurantDTO restaurantDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         @RequestParam(value = "files", required = false) MultipartFile[] files) {
        log.info("restaurant Modify register.......");

        if (bindingResult.hasErrors()) {
            log.info("has errors.......");
            String link = pageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("rno", restaurantDTO.getRno());
            return "redirect:/restaurant/modify?" + link;
        }

        if (files != null && files.length > 0 && !files[0].isEmpty()) {
            // 이미지 업로드 처리
            List<UploadResultDTO> uploadResults = new ArrayList<>();
            int ord = 0; // 순번 초기화
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
                        .ord(ord++) // 순번 설정
                        .build());
            }

            // 이미지 업데이트
            restaurantService.updateImages(restaurantDTO.getRno(), uploadResults);
        }

        // 레스토랑 정보 업데이트
        restaurantService.update(restaurantDTO);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("rno", restaurantDTO.getRno());
        return "redirect:/restaurant/read?";
    }

    @PreAuthorize("principal.username == #restaurantDTO.rno")
    @PostMapping("/remove")
    public String remove(RestaurantDTO restaurantDTO, RedirectAttributes redirectAttributes) {
        log.info("restaurant Remove.......");
        int rno = restaurantDTO.getRno();
        log.info("remove post ... " + rno);
        restaurantService.delete(rno);
        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/restaurant/list";
    }
}
