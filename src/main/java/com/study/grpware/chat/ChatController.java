package com.study.grpware.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.grpware.member.Member;
import com.study.grpware.member.MemberDto;
import com.study.grpware.member.MemberService;
import com.study.grpware.util.CommonUtils;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
public class ChatController {

    private final MemberService memberService;

    @GetMapping(value = "/index")
    public String goChatIndex(Model model){

        List<Member> memberList = memberService.findAll();
        List<MemberDto> memberDtoList = new ArrayList<>();

        memberList.stream().forEach(m -> {
            m.setMemberBirth(CommonUtils.changeDateFormat(m.getMemberBirth()));
            m.setMemberNumber(CommonUtils.changeNumFormat(m.getMemberNumber()));
            memberDtoList.add(MemberDto.of(m));
        });
        
        model.addAttribute("memberDtoList", memberDtoList);

        return "/chat/index";
    }

}
