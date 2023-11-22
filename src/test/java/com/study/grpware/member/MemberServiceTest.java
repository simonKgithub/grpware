package com.study.grpware.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional //테스트에서 선언할 경우 테스트 종료 후 rollback 됨
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceTest {

    @Autowired MemberService memberService;

    @Autowired MemberRepository memberRepository;

    @Test
    @DisplayName("임시 비밀번호 불일치 테스트")
    void tmpPasswordTest(){
        // 회원가입 양식으로 회원가입 함
        MemberFormDto dto1 = createMemberForm();
        MemberFormDto newDto1 = memberService.registerMember(dto1);

        // 방금 가입한 회원을 이메일로 검색
        Member origin = memberRepository.findByEmail(newDto1.getEmailId() + newDto1.getEmailAddress());
        MemberDto ori = MemberDto.of(origin);

        // 비밀번호 찾으면서 변경
        MemberDto byEmail = memberService.findByEmailAndNumber(ori);

        // 아이디, 이메일 등 다 같으나
        assertEquals(byEmail.getEmail(), ori.getEmail());
        assertEquals(byEmail.getMemberName(), ori.getMemberName());
        assertEquals(byEmail.getMemberNumber(), ori.getMemberNumber());
        assertEquals(byEmail.getMemberBirth(), ori.getMemberBirth());
        // 비번은 달라야 함
        assertNotEquals(byEmail.getPassword(), ori.getPassword());
//        assertEquals(byEmail.getPassword(), ori.getPassword());
    }

    @Test
    @DisplayName("회원 중복 테스트")
    void findMember() {
        MemberFormDto dto1 = createMemberForm();
        MemberFormDto dto2 = createMemberForm();
        MemberFormDto savedDto = memberService.registerMember(dto1);

        Throwable e = assertThrows(IllegalStateException.class, () -> {
            memberService.registerMember(dto2);
        });

        assertEquals("이미 가입된 회원입니다.", e.getMessage());
    }

    @Test
    @DisplayName("회원 등록 테스트")
    void registerMember() {
        MemberFormDto dto = createMemberForm();
        MemberFormDto savedMember = memberService.registerMember(dto);

        assertEquals(dto.getEmailId(), savedMember.getEmailId());
        assertEquals(dto.getEmailAddress(), savedMember.getEmailAddress());
        assertEquals(dto.getPassword(), savedMember.getPassword());
        assertEquals(dto.getMemberName(), savedMember.getMemberName());
        assertEquals(dto.getMemberNumber(), savedMember.getMemberNumber());
        assertEquals(dto.getBirthYYYY(), savedMember.getBirthYYYY());
        assertEquals(dto.getBirthMM(), savedMember.getBirthMM());
        assertEquals(dto.getBirthDD(), savedMember.getBirthDD());
    }

    private static MemberDto createMember(){
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail("email@address");
        memberDto.setPassword("1234");
        memberDto.setMemberName("dingko");
        memberDto.setMemberNumber("010-4153-9702");
        memberDto.setMemberBirth("1994-06-14");
        return memberDto;
    }

    private static MemberFormDto createMemberForm(){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmailId("email");
        memberFormDto.setEmailAddress("@address");
        memberFormDto.setPassword("1234");
        memberFormDto.setMemberName("dingko");
        memberFormDto.setMemberNumber("010-4153-9702");
        memberFormDto.setBirthYYYY("1994");
        memberFormDto.setBirthMM("06");
        memberFormDto.setBirthDD("14");
        return memberFormDto;
    }
}