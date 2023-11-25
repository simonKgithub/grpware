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

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final EmailService emailService;

    /**
     * 회원등록 ( memberFormDto 진행됨)
     * @param memberFormDto
     * @return MemberFormDto
     */
    public MemberFormDto registerMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = Member.createMember(memberFormDto, passwordEncoder);
        validateDuplicationMember(member);
        Member savedMember = memberRepository.save(member);
        return MemberFormDto.of(savedMember);
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
