package com.study.grpware.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/memberJoin")
    public String goToMemberJoinPage(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberJoinForm";
    }

    @PostMapping("/memberJoin")
    public String memberJoinProcess(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "member/memberJoinForm";
        }
        try {
            MemberFormDto savedDto = memberService.registerMember(memberFormDto, passwordEncoder);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberJoinForm";
        }
        return "redirect:/login?register="+true;
    }
}
