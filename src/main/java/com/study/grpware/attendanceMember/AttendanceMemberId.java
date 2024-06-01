package com.study.grpware.attendanceMember;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class AttendanceMemberId implements Serializable {
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "reg_date")
    private String regDate;
}