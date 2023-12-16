package com.study.grpware.member;

import com.study.grpware.constant.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member implements UserDetails {

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

    /**
     * Builder 패턴 적용
     * 프로젝트 open 시 Member 객체가 처음 초기화 되므로, Role 객체로 초기화하여 적용해줘야 함
     * 따라서, @AllArgsConstructor 를 써서 자동 생성 및 주입을 시켜줌
     * @param memberDto
     * @param passwordEncoder
     * @return
     */
    public static Member createMember(MemberDto memberDto, PasswordEncoder passwordEncoder) {
        String encodedPassword = passwordEncoder.encode(memberDto.getPassword());
        return Member.builder()
                .email(memberDto.getEmail())
                .password(encodedPassword)
                .memberName(memberDto.getMemberName())
                .memberNumber(memberDto.getMemberNumber())
                .memberBirth(memberDto.getMemberBirth())
                .role(Role.USER)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(getRole().getAuthority()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
