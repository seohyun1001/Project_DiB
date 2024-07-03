package org.zerock.project_dib.pse.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.project_dib.pse.dto.FaqDTO;
import org.zerock.project_dib.pse.dto.PageRequestDTO;
import org.zerock.project_dib.pse.dto.PageResponseDTO;
import org.zerock.project_dib.pse.service.FaqService;

import java.time.LocalDate;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/faq")
public class FaqController {

    @Autowired
    private FaqService faqService;

    // FAQ 목록을 가져오는 GET 메소드
    @GetMapping("")
    public String getAllFaqs(@ModelAttribute PageRequestDTO pageRequestDTO, Model model, RedirectAttributes redirectAttributes) {
        try {
            if (pageRequestDTO == null) {
                pageRequestDTO = new PageRequestDTO(); // 페이지 요청 객체가 null일 경우 초기화
            }
            pageRequestDTO.calculateOffset(); // 오프셋 계산
            PageResponseDTO<FaqDTO> responseDTO = faqService.search(pageRequestDTO);
            model.addAttribute("faqs", responseDTO.getDtoList());
            model.addAttribute("pageRequestDTO", pageRequestDTO);
            model.addAttribute("totalPage", responseDTO.getTotalPage());
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/faq";
        }
        return "faq/faq";
    }

    // 검색을 처리하는 POST 메소드
    @PostMapping("")
    public String searchFaqs(@ModelAttribute PageRequestDTO pageRequestDTO, Model model) {
        try {
            pageRequestDTO.calculateOffset(); // 오프셋 계산
            PageResponseDTO<FaqDTO> responseDTO = faqService.search(pageRequestDTO);
            model.addAttribute("faqs", responseDTO.getDtoList());
            model.addAttribute("pageRequestDTO", pageRequestDTO);
            model.addAttribute("totalPage", responseDTO.getTotalPage());
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/faq";
        }
        return "faq/faq";
    }


    @GetMapping("/register")
    public String showRegisterForm() {
        return "faq/faq_register";
    }

    @PostMapping("/register")
    public String registerFaq(MultipartFile file, FaqDTO faqDTO) {
        faqService.register(faqDTO);
        return "redirect:/faq";
    }

    @GetMapping("/detail/{fno}")
    public String readFaq(@PathVariable Long fno, Model model) {
        FaqDTO faqDTO = faqService.readOne(fno);
        model.addAttribute("faq", faqDTO);
        return "faq/faq_detail"; // FAQ 상세 페이지로 이동
    }

    @GetMapping("/modify/{fno}")
    public String showModifyForm(@PathVariable Long fno, Model model) {
        FaqDTO faqDTO = faqService.readOne(fno);
        model.addAttribute("faq", faqDTO);
        return "faq/faq_modify"; // 수정 페이지로 이동
    }

    @PostMapping("/modify")
    public String modifyFaq(@ModelAttribute FaqDTO faqDTO) {
        faqDTO.setModdate(LocalDate.now()); // 수정일자를 현재 날짜로 설정
        System.out.println("Modifying Faq: " + faqDTO);
        faqService.modify(faqDTO);
        return "redirect:/faq";
    }

    @PostMapping("/delete/{fno}")
    public String deleteFaq(@PathVariable Long fno) {
        faqService.remove(fno);
        return "redirect:/faq";
    }
}
