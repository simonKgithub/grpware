package com.study.grpware.member;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Table(name = "member")
@Getter @Setter
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
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
}
