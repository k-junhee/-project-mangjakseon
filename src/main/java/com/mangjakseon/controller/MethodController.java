package com.mangjakseon.controller;

import com.mangjakseon.dto.MemberDTO;
import com.mangjakseon.security.validation.CheckEmailValidator;
import com.mangjakseon.security.validation.CheckNicknameValidator;
import com.mangjakseon.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.Map;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MethodController {

    /* Spring Security 담당 김준희 ********************************************/
    private final MemberService memberService;

    private final CheckEmailValidator checkEmailValidator;
    private final CheckNicknameValidator checkNicknameValidator;

    // 커스텀 유효성 검증
    @InitBinder
    public void validatorBinder(WebDataBinder binder){
        binder.addValidators(checkEmailValidator);
        binder.addValidators(checkNicknameValidator);
    }

    // 회원가입
    @PostMapping("/register")
    public Object register(@Valid MemberDTO memberDTO, Errors errors, Model model)
            throws URISyntaxException {
        if (errors.hasErrors()){
            // 회원가입 실패시 입력 데이터값 유지
            model.addAttribute("memberDTO",memberDTO);

            // 유효성 통과 못한 필드와 메세지 처리
            Map<String,String> validatorResult = memberService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            return "/sign-up";
        }

        memberService.checkEmailDuplication(memberDTO);
        memberService.checkNicknameDuplication(memberDTO);

        memberService.register(memberDTO);

        return "redirect:/index";
    }
    // 회원정보 수정
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify")
    // 이름이 "profileImgUrl"인 파일을 MultipartFile 객체에 담아 처리
    public String modify(MemberDTO memberDTO, @RequestParam("profileImgUrl")MultipartFile multipartFile) {
        memberService.modify(memberDTO, multipartFile);
        return "redirect:/read";
    }
    // 회원탈퇴
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/remove")
    public String remove(String memberId, HttpServletResponse response){
        memberService.remove(memberId);

        Cookie cookie = new Cookie("JSESSIONID",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){
        return "/index";
    }

    @GetMapping("/index/login")
    public String ilogin(){
        return "/index";
    }

    // 회원정보 수정시 닉네임 중복 체크
    @PostMapping("/nickChk")
    @ResponseBody
    public int nickChk(@RequestParam("nickname")String nickname){
        log.info(nickname);

        return memberService.nicknameCheck(nickname);
    }

    @PostMapping("/loginChk")
    @ResponseBody
    public boolean loginChk(@RequestParam("email")String email,
                            @RequestParam("password")String password,
                            Model model){
        log.info("INFO CHECK ::: ");
        log.info(email);
        log.info(password);
        return memberService.accountCheck(email, password);
    }

    // 로그인 에러시 메시지
//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    @GetMapping("/login")
//    public String login(@RequestParam(value = "error", required = false) String error,
//                        @RequestParam(value = "exception", required = false) String exception,
//                        RedirectAttributes attr
//                        ){
////        model.addAttribute("error", error);
////        model.addAttribute("exception", exception);
//
//        attr.addFlashAttribute("error", error);
//        attr.addFlashAttribute("exception", exception);
//        return "/index";
//    }
//
//    @GetMapping("/index/login")
//    public String error(@RequestParam(value = "error", required = false) String error,
//                        @RequestParam(value = "exception", required = false) String exception,
//                        Model model){
//        log.info("ERROR PAGE CHECK !!!");
//        model.addAttribute("error", error);
//        model.addAttribute("exception", exception);
//        model.addAttribute("check",1);
//
//        return "/index";
//    }
    /* Spring Security 담당 김준희 */
}
