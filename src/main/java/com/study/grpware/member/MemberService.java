package com.study.grpware.member;

import com.study.grpware.util.email.EmailDto;
import com.study.grpware.util.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final EmailService emailService;

    /**
     * 회원등록 ( memberFormDto 진행됨)
     * @param memberFormDto
     * @return MemberFormDto
     */
    public MemberFormDto registerMember(MemberFormDto memberFormDto) {
        Member member = MemberFormDto.to(memberFormDto);
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
    public MemberDto findByEmailAndNumber(MemberDto memberDto) {
        Member byEmail = memberRepository.findByEmailAndMemberNumber(memberDto.getEmail(), memberDto.getMemberNumber());
        if (byEmail == null) {
            throw new IllegalStateException("등록된 회원 정보가 없습니다.");
        }

        String tmpPassword = emailService.emailPassword(byEmail.getEmail());
        // 비밀번호 변경
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
}
