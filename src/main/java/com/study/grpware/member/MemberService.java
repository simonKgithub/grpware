package com.study.grpware.member;

import com.study.grpware.util.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final EmailService emailService;

    /**
     * 회원등록 ( memberFormDto 진행됨)
     * @param memberDto
     * @return MemberFormDto
     */
    public MemberDto registerMember(MemberDto memberDto, PasswordEncoder passwordEncoder) {
        validatePassword(memberDto.getPassword()); //비밀번호 검증
        Member member = Member.createMember(memberDto, passwordEncoder);
        validateDuplicationMember(member); //중복검증
        Member savedMember = memberRepository.save(member);
        return MemberDto.of(savedMember);
    }

    /**
     * 회원 찾기(이름, 핸드폰번호)
     * @param memberDto memberName, memberNumber
     * @return MemberDto
     */
    public MemberDto findByNameAndNumber(MemberDto memberDto) {
        Member byName = memberRepository.findByMemberNameAndMemberNumber(memberDto.getMemberName(), memberDto.getMemberNumber());
        if (byName == null) {
            throw new IllegalStateException("등록된 회원 정보가 없습니다.");
        }
        return MemberDto.of(byName);
    }

    /**
     * 회원 찾기(이메일, 핸드폰번호)
     * @param memberDto email, memberNumber
     * @return memberDto
     */
    public MemberDto findByEmailAndNumber(MemberDto memberDto, PasswordEncoder passwordEncoder) {
        Member byEmail = memberRepository.findByEmailAndMemberNumber(memberDto.getEmail(), memberDto.getMemberNumber());
        if (byEmail == null) {
            throw new IllegalStateException("등록된 회원 정보가 없습니다.");
        }

        // 비밀번호 변경
        String tmpPassword = emailService.emailPassword(byEmail.getEmail());
        tmpPassword = passwordEncoder.encode(tmpPassword);
        byEmail.setPassword(tmpPassword);
        Member savedMember = memberRepository.save(byEmail);
        return MemberDto.of(savedMember);
    }

    /**
     * 회원 중복 검증 (내부 메서드)
     * @param member
     */
    private void validateDuplicationMember(Member member) {
        Member dbMember = memberRepository.findByEmail(member.getEmail());
        if (dbMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    /**
     * 비밀번호 유효성 검증 (최소 8자 이상, 숫자, 특수문자 포함)
     * @param password
     */
    private void validatePassword(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new IllegalStateException("비밀번호는 최소 8자 이상, 숫자, 특수문자가 포함되어야 합니다.");
        }
    }

    /**
     * 스프링 시큐리티 - 로그인
     * @param email
     * @return User (스프링 제공)
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member byEmail = memberRepository.findByEmail(email);
        if (byEmail == null) {
            throw new UsernameNotFoundException(email);
        }
        return byEmail;
    }
}
