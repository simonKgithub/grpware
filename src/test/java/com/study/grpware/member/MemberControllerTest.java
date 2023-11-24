package com.study.grpware.member;

import com.study.grpware.constant.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberControllerTest {

    @Autowired private MemberService memberService;

    @Autowired private MockMvc mockMvc;

    @Autowired PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("로그인 실패 테스트(비밀버호 오기입)")
    void loginFailureTest() throws Exception {
        String email = "dingkoshop@email.com";
        String password = "1234";
        MemberFormDto newForm = this.createMemberForm(email, password);

        mockMvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/login")
                .user(newForm.getEmailId() + newForm.getEmailAddress())
                .password(password+1)
        ).andExpect(SecurityMockMvcResultMatchers.unauthenticated());

    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void loginSuccessTest() throws Exception {
        String email = "dingkoshop@email.com";
        String password = "1234";
        MemberFormDto newForm = this.createMemberForm(email, password);

        mockMvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/login")
                .user(newForm.getEmailId() + newForm.getEmailAddress())
                .password(password)
        ).andExpect(SecurityMockMvcResultMatchers.authenticated());

    }

    private MemberFormDto createMemberForm(String email, String password){
        MemberFormDto memberFormDto = new MemberFormDto();
        String[] emailArr = email.split("@");
        memberFormDto.setEmailId(emailArr[0]);
        memberFormDto.setEmailAddress("@"+emailArr[1]);
        memberFormDto.setPassword(password);
        memberFormDto.setMemberName("dingko");
        memberFormDto.setMemberNumber("010-4153-9702");
        memberFormDto.setBirthYYYY("1994");
        memberFormDto.setBirthMM("06");
        memberFormDto.setBirthDD("14");
        return memberService.registerMember(memberFormDto, passwordEncoder);
    }

}