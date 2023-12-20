package com.study.grpware.attendance;

import com.study.grpware.member.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity @Table(name = "attendance")
@Getter @Setter @ToString
public class AttendanceEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "attend_id")
    private Long attendId;

    @Column(name = "place_name")
    private String placeName;

    @Column(name = "address")
    private String address;

    @Column(name = "lat")
    private String lat;

    @Column(name = "lng")
    private String lng;

    @Column(name = "rep_yn")
    private boolean repYn;

    @Column(name = "reg_date")
    private String regDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}