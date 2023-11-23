package com.study.grpware.member;

import com.study.grpware.constant.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "member_number")
    private String memberNumber;

    @Column(name = "member_birth")
    private String memberBirth;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setEmail(memberFormDto.getEmailId() + memberFormDto.getEmailAddress());
        String encodedPassword = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(encodedPassword);
        member.setMemberName(memberFormDto.getMemberName());
        member.setMemberNumber(memberFormDto.getMemberNumber());
        member.setMemberBirth(memberFormDto.getBirthYYYY() + "-" + memberFormDto.getBirthMM() + "-" + memberFormDto.getBirthDD());
        member.setRole(Role.USER);
        return member;
    }
}
