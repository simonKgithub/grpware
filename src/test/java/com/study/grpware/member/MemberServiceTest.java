package com.study.grpware.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional //테스트에서 선언할 경우 테스트 종료 후 rollback 됨
@TestPropertySource(locations = "classpath:application.properties")
class MemberServiceTest {

    @Autowired MemberService memberService;

    @Autowired MemberRepository memberRepository;

    @Autowired PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("비밀번호 유효성 체크(통과)")
    void passwordValidationTest(){
        MemberDto memberDto = this.createMember();
        memberDto.setPassword("qlalfqjsgh1!");
        MemberDto savedDto = memberService.registerMember(memberDto, passwordEncoder);

        assertEquals(memberDto.getEmail(), savedDto.getEmail());
        assertEquals(memberDto.getMemberName(), savedDto.getMemberName());
        assertEquals(memberDto.getMemberNumber(), savedDto.getMemberNumber());
        assertEquals(memberDto.getMemberBirth(), savedDto.getMemberBirth());
//        assertEquals(memberDto.getRole(), savedDto.getRole());
    }

    @Test
    @DisplayName("비밀번호 유효성 체크(오류던짐)")
    void passwordValidationThrowTest(){
        MemberDto memberDto = this.createMember();
//        memberDto.setPassword("12345a!"); //8자 이하
//        memberDto.setPassword("aaaaaaa!"); //숫자 미포함
        memberDto.setPassword("1234567a"); //특수문자 미포함

        Throwable e = assertThrows(IllegalStateException.class, () -> {
            memberService.registerMember(memberDto, passwordEncoder);
        });

        assertEquals("비밀번호는 최소 8자 이상, 숫자, 특수문자가 포함되어야 합니다.", e.getMessage());
    }

    @Test
    @DisplayName("임시 비밀번호 불일치 테스트")
    void tmpPasswordTest(){
        // 회원가입 양식으로 회원가입 함
        Member dto1 = createMemberForm();
        MemberDto formDto1 = MemberDto.of(dto1);
        MemberDto newDto1 = memberService.registerMember(formDto1, passwordEncoder);

        // 방금 가입한 회원을 이메일로 검색
        Member origin = memberRepository.findByEmail(newDto1.getEmail());
        MemberDto ori = MemberDto.of(origin);

        // 비밀번호 찾으면서 변경
        MemberDto byEmail = memberService.findByEmailAndNumber(ori, passwordEncoder);

        // 아이디, 이메일 등 다 같으나
        assertEquals(byEmail.getEmail(), ori.getEmail());
        assertEquals(byEmail.getMemberName(), ori.getMemberName());
        assertEquals(byEmail.getMemberNumber(), ori.getMemberNumber());
        assertEquals(byEmail.getMemberBirth(), ori.getMemberBirth());
        // 비번은 달라야 함
        assertNotEquals(byEmail.getPassword(), ori.getPassword());
    }

    @Test
    @DisplayName("회원 중복 테스트")
    void findMember() {
        Member member1 = createMemberForm();
        Member member2 = createMemberForm();
        MemberDto formDto1 = MemberDto.of(member1);
        MemberDto formDto2 = MemberDto.of(member2);
        MemberDto savedDto1 = memberService.registerMember(formDto1, passwordEncoder);

        Throwable e = assertThrows(IllegalStateException.class, () -> {
            memberService.registerMember(formDto2, passwordEncoder);
        });

        assertEquals("이미 가입된 회원입니다.", e.getMessage());
    }

    @Test
    @DisplayName("회원 등록 테스트")
    void registerMember() {
        Member member = createMemberForm();
        Member savedMember = memberRepository.save(member);

        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getPassword(), savedMember.getPassword());
        assertEquals(member.getMemberName(), savedMember.getMemberName());
        assertEquals(member.getMemberNumber(), savedMember.getMemberNumber());
        assertEquals(member.getRole(), savedMember.getRole());
    }

    private MemberDto createMember(){
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail("email@address");
        memberDto.setPassword("1234");
        memberDto.setMemberName("dingko");
        memberDto.setMemberNumber("01041539702");
        memberDto.setMemberBirth("19940614");
        return memberDto;
    }

    private Member createMemberForm(){
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail("email@address");
        memberDto.setPassword("1234");
        memberDto.setMemberName("dingko");
        memberDto.setMemberNumber("01041539702");
        memberDto.setMemberBirth("19940614");
        return Member.createMember(memberDto, passwordEncoder);
    }
}