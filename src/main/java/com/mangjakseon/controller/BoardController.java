package com.mangjakseon.controller;

import com.mangjakseon.dto.BoardDTO;
import com.mangjakseon.dto.PageRequestDTO;
import com.mangjakseon.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
/*

    @Value("${org.zerock.upload.path}")
    private String uploadPath;
*/


    // 목록
    @GetMapping("/board_list")
    public void list(PageRequestDTO pageRequestDTO, Integer bno, Model model){
        model.addAttribute("result",boardService.getList(pageRequestDTO)); // PageRequestDTO에 담긴정보를 service의 getList로 보내서 result에 넣고 뷰에서 사용한다
    }

    // 등록
    @GetMapping("/board_register")
    public void register() {

    }

    @PostMapping("/board_register")
    public String registerPost(BoardDTO boardDTO, RedirectAttributes redirectAttributes, MultipartFile[] uploadFiles){
        Integer bno = boardService.register(boardDTO);

        redirectAttributes.addFlashAttribute("msg",bno);

        return "redirect:/board_list";
    }

    // 상세보기
    @GetMapping({"/board_read","/board_modify"})
    public void read(Integer bno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        boardService.updateView(bno); // 조회수 코드
        BoardDTO dto = boardService.read(bno);
        model.addAttribute("dto", dto);
    }


    // 삭제
    @PostMapping("/board_remove")
    public String remove(Integer bno, RedirectAttributes redirectAttributes){
        boardService.remove(bno);
        redirectAttributes.addFlashAttribute("msg",bno);
        return "redirect:/board_list";
    }

    // 수정
    @PostMapping("/board_modify")
    public String modify(BoardDTO boardDTO, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        boardService.modify(boardDTO);

        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("bno",boardDTO.getBno());

        return "redirect:/board_read";
    }

    /*@PostMapping("/createsum")
    public String createsumPost(BoardDTO boardDTO, RedirectAttributes redirectAttributes, MultipartFile[] uploadFiles){


        return "redirect:/board/list";
    }
*/
}
