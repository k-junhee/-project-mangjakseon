package com.mangjakseon.controller;

import com.mangjakseon.dto.MemberDTO;
import com.mangjakseon.repository.MemberRepository;
import com.mangjakseon.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@Log4j2
@RequiredArgsConstructor
public class PageController {

    /* Spring Security 담당 김준희 ********************************************/
    private final MemberService memberService;
    private final MemberRepository repository;

    @GetMapping("/sign-up") // 기본 회원가입 페이지
    public void signup(){}

    // 회원정보 상세보기 및 수정 페이지
    @PreAuthorize("isAuthenticated()")
    @GetMapping({"/read","/modify"})
    public void read(Principal principal, String memberId, Model model){
        memberId = repository.findByMemberId(principal.getName());
        MemberDTO memberDTO = memberService.read(memberId);
        model.addAttribute("dto",memberDTO);
    }

    @RequestMapping(value = "/test{keyword}")
    @GetMapping("/test")
    public void test(String keyword, Model model){
        model.addAttribute("key",keyword);
    }
    /* Spring Security 담당 김준희 */

}
