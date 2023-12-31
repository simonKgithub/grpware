package com.study.grpware.login;

import com.study.grpware.member.MemberDto;
import com.study.grpware.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String goToLoginPage(Model model){
        return "login/loginPage";
    }

    @GetMapping("/login/error")
    public String goToLoginPageWithError(Model model){
        model.addAttribute("loginErrorMsg", "이메일 또는 비밀번호를 확인해주세요.");
        return "login/loginPage";
    }

    @GetMapping("/login/accessDenied")
    public String goToLoginPageWithAccessDenied(Model model){
        model.addAttribute("accessDenied", "계정이 비활성화 상태입니다. 관리자에게 문의해주세요.");
        return "login/loginPage";
    }

    @GetMapping("/login/logout")
    public String logoutSuccess(Model model){
        model.addAttribute("logoutMsg", "로그아웃이 완료되었습니다.");
        return "login/loginPage";
    }

    @GetMapping("/forget")
    public String goToForgetPage(){
        return "login/forgetPage";
    }

    @PostMapping("/forget")
    @ResponseBody
    public String findMemberProcess(@RequestBody MemberDto memberDto, Model model) {
        String msg = "";
        if ( memberDto.getMemberNumber() != null ) {
            //이메일 찾기
            if (memberDto.getMemberName() != null) {
                try {
                    MemberDto byNameDto = memberService.findByNameAndNumber(memberDto);
                    msg = "이메일은 " + byNameDto.getEmail() + " 입니다.";
                } catch (IllegalStateException e) {
                    return e.getMessage();
                }
            }
            //비밀번호 찾기
            else if (memberDto.getEmail() != null) {
                try {
                    MemberDto byEmail = memberService.findByEmailAndNumber(memberDto, passwordEncoder);
                    msg = "임시 비밀번호가 발급되었습니다.\n이메일을 확인해주세요.";
                } catch (IllegalStateException e) {
                    return e.getMessage();
                }
            }
        } else {
            throw new IllegalStateException("잘못된 입력입니다.");
        }
        return msg;
    }
}