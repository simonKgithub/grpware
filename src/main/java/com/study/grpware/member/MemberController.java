package com.study.grpware.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/member")
public class MemberController {

    @GetMapping("/memberJoin")
    public String goToMemberJoinPage(){
        return "member/memberJoinForm";
    }
}
