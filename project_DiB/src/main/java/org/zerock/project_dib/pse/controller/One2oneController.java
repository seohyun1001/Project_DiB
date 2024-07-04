package org.zerock.project_dib.pse.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.project_dib.pse.dto.One2oneDTO;
import org.zerock.project_dib.pse.dto.PageRequestDTO;
import org.zerock.project_dib.pse.dto.PageResponseDTO;
import org.zerock.project_dib.pse.service.One2oneService;

import java.time.LocalDate;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/one2one")
public class One2oneController {

    @Autowired
    private One2oneService one2oneService;

    // 1대1 문의 목록을 가져오는 GET 메소드
    @GetMapping("")
    public String getAllOne2ones(@ModelAttribute PageRequestDTO pageRequestDTO, Model model, RedirectAttributes redirectAttributes) {
        try {
            if (pageRequestDTO == null) {
                pageRequestDTO = new PageRequestDTO(); // 페이지 요청 객체가 null일 경우 초기화
            }
            pageRequestDTO.calculateOffset(); // 오프셋 계산
            PageResponseDTO<One2oneDTO> responseDTO = one2oneService.search(pageRequestDTO);
            model.addAttribute("one2ones", responseDTO.getDtoList());
            model.addAttribute("pageRequestDTO", pageRequestDTO);
            model.addAttribute("totalPage", responseDTO.getTotalPage());
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/one2one";
        }
        return "one2one/one2one";
    }

    // 검색을 처리하는 POST 메소드
    @PostMapping("")
    public String searchOne2ones(@ModelAttribute PageRequestDTO pageRequestDTO, Model model) {
        try {
            pageRequestDTO.calculateOffset(); // 오프셋 계산
            PageResponseDTO<One2oneDTO> responseDTO = one2oneService.search(pageRequestDTO);
            model.addAttribute("one2ones", responseDTO.getDtoList());
            model.addAttribute("pageRequestDTO", pageRequestDTO);
            model.addAttribute("totalPage", responseDTO.getTotalPage());
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/one2one";
        }
        return "one2one/one2one";
    }

    // 등록 폼을 보여주는 GET 메소드
    @GetMapping("/register")
    public String showRegisterForm(Model model, @AuthenticationPrincipal User user) {
        // 사용자 이름을 모델에 추가하여 템플릿에서 사용 가능하도록 함
        model.addAttribute("username", user.getUsername());
        return "one2one/one2one_register";
    }

    // 등록 처리를 하는 POST 메소드
    @PostMapping("/register")
    public String registerOne2one(MultipartFile file, One2oneDTO one2oneDTO, @AuthenticationPrincipal User user) {
        one2oneDTO.setMid(user.getUsername()); // 작성자 ID 설정
        one2oneService.register(one2oneDTO);
        return "redirect:/one2one";
    }

    @GetMapping("/detail/{otono}")
    public String readOne2one(@PathVariable Long otono, Model model) {
        One2oneDTO one2oneDTO = one2oneService.readOne(otono);
        model.addAttribute("one2one", one2oneDTO);
        return "one2one/one2one_detail"; // 1대1 문의 상세 페이지로 이동
    }

    @GetMapping("/modify/{otono}")
    public String showModifyForm(@PathVariable Long otono, Model model) {
        One2oneDTO one2oneDTO = one2oneService.readOne(otono);
        model.addAttribute("one2one", one2oneDTO);
        return "one2one/one2one_modify"; // 수정 페이지로 이동
    }

    @PostMapping("/modify")
    public String modifyOne2one(@ModelAttribute One2oneDTO one2oneDTO) {
        one2oneDTO.setModdate(LocalDate.now()); // 수정일자를 현재 날짜로 설정
        System.out.println("Modifying One2one: " + one2oneDTO);
        one2oneService.modify(one2oneDTO);
        return "redirect:/one2one";
    }

    @PostMapping("/delete/{otono}")
    public String deleteOne2one(@PathVariable Long otono) {
        one2oneService.remove(otono);
        return "redirect:/one2one";
    }
}
