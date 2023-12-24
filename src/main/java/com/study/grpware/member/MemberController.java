package com.study.grpware.member;

import com.study.grpware.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 사용자 권한 변경
     * @param memberDto
     * @return
     */
    @PatchMapping("/roleChange")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<String> roleChange(@RequestBody MemberDto memberDto) {
        Member member = memberService.roleChange(memberDto);
        String message = "사용자의 계정이" + member.getRole() + "으로 변경되었습니다.";

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * 사용자 계정 활성화
     * @param memberDto
     * @return
     */
    @PatchMapping("/enabledChange")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<String> enabledChange(@RequestBody MemberDto memberDto){
        Member member = memberService.enabledChange(memberDto);
        String message = member.isEnabled() ? "계정이 활성화 되었습니다." : "계정이 비활성화 되었습니다.";

        return new ResponseEntity<>(message ,HttpStatus.OK);
    }

    /**
     * 이동: 회원 관리 페이지 이동
     * @param model
     * @return
     */
    @GetMapping("memberManage")
    @PreAuthorize("hasRole('ADMIN')")/* Admin 만 접근 가능 */
    public String goToMemberListPage(Model model) {
        List<Member> memberList = memberService.findAll();
        List<MemberDto> memberDtoList = new ArrayList<>();
        memberList.stream().forEach(m -> {
            // 뷰에 보기 좋게 설정
            m.setMemberBirth(CommonUtils.changeDateFormat(m.getMemberBirth()));
            m.setMemberNumber(CommonUtils.changeNumFormat(m.getMemberNumber()));
            memberDtoList.add(MemberDto.of(m));
        });
        model.addAttribute("memberDtoList", memberDtoList);

        return "member/memberManagePage";
    }

    /**
     * 이동: 회원 가입 페이지 이동
     * @param model
     * @return
     */
    @GetMapping("/memberJoin")
    public String goToMemberJoinPage(Model model){
        model.addAttribute("memberDto", new MemberDto());
        return "member/memberJoinForm";
    }

    /**
     * 저장: 회원 가입
     * @param memberDto
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping("/memberJoin")
    public String memberJoinProcess(@Valid MemberDto memberDto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "member/memberJoinForm";
        }
        try {
            MemberDto savedDto = memberService.registerMember(memberDto, passwordEncoder);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberJoinForm";
        }
        return "redirect:/login?register="+true;
    }
}
