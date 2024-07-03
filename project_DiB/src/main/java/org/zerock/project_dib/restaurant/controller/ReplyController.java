//package org.zerock.project_dib.restaurant.controller;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.zerock.project_dib.restaurant.dto.ReplyDTO;
//
//@Controller
//@RequestMapping("/replies")
//@RequiredArgsConstructor
//@Log4j2
//public class ReplyController {
//
//    private final ReplyService replyService;
//
//    // 댓글 등록
//    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public String register(@Valid @RequestBody ReplyDTO replyDTO, BindingResult bindingResult, Model model) throws BindException {
//        log.info(replyDTO);
//
//        // 유효성 검사에서 오류가 발생한 경우 예외를 발생시킴
//        if (bindingResult.hasErrors()) {
//            throw new BindException(bindingResult);
//        }
//
//        // 댓글 등록 서비스 호출
//        Long rno = replyService.register(replyDTO);
//        model.addAttribute("rno", rno);
//
//        // 등록 후 댓글 목록 페이지로 리다이렉트
//        return "redirect:/replies/list/" + replyDTO.getBno();
//    }
//
//    // 특정 게시물의 댓글 목록 조회
//    @GetMapping(value = "/list/{bno}")
//    public String getList(@PathVariable("bno") Long bno, PageRequestDTO pageRequestDTO, Model model) {
//        PageResponseDTO<ReplyDTO> responseDTO = replyService.getListOfBoard(bno, pageRequestDTO);
//        model.addAttribute("result", responseDTO);
//        return "replies/list"; // 댓글 목록을 표시할 Thymeleaf 템플릿 이름 반환
//    }
//
//    // 특정 댓글 조회
//    @GetMapping(value = "/{rno}")
//    public String getReplyDto(@PathVariable("rno") Long rno, Model model) {
//        ReplyDTO replyDTO = replyService.read(rno);
//        model.addAttribute("reply", replyDTO);
//        return "replies/read"; // 댓글을 표시할 Thymeleaf 템플릿 이름 반환
//    }
//
//    // 특정 댓글 삭제
//    @DeleteMapping(value = "/{rno}")
//    public String remove(@PathVariable("rno") Long rno, Model model) {
//        replyService.remove(rno);
//        model.addAttribute("rno", rno);
//        return "redirect:/replies/list"; // 댓글 목록 페이지로 리다이렉트
//    }
//
//    // 특정 댓글 수정
//    @PutMapping(value = "/{rno}", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public String modify(@PathVariable("rno") Long rno, @RequestBody ReplyDTO replyDTO, Model model) {
//        replyDTO.setRno(rno);
//        replyService.modify(replyDTO);
//        model.addAttribute("rno", rno);
//        return "redirect:/replies/" + rno; // 수정된 댓글 페이지로 리다이렉트
//    }
//}