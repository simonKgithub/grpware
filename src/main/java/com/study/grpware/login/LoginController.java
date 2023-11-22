package com.study.grpware.login;

import com.study.grpware.member.MemberDto;
import com.study.grpware.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String goToLoginPage(Model model){
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
                    MemberDto byEmail = memberService.findByEmailAndNumber(memberDto);
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