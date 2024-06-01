package com.study.grpware.attendanceMember;

import lombok.*;

import javax.persistence.*;

@Entity @Table(name="attendance_member")
@Getter @Setter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor
public class AttendanceMember {

    /** 멤버 ID + 출석일 */
    @EmbeddedId
    private AttendanceMemberId id;

    /** 멤버 이름 */
    @Column(name = "member_name")
    private String memberName;

    /** 출석 장소 */
    @Column(name = "place_name")
    private String placeName;

    /** 출석시간 HHMMSS */
    @Column(name = "reg_time")
    private String regTime;

    /** 출결 여부 */
    @Column(name = "attend_yn")
    private char attendYn;

    /** 사유 */
    @Column(name = "reason")
    private String reason;
}
