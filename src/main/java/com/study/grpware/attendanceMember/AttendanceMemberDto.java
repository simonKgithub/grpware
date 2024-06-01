package com.study.grpware.attendanceMember;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Getter @Setter @ToString
public class AttendanceMemberDto {

    /** 멤버 ID */
    public AttendanceMemberId id;

    /** 멤버 이름 */
    public String memberName;

    /** 출석 장소 */
    public String placeName;

    /** 출석일시 yyyymmddhhmmss */
    public String regTime;

    /** 출결 여부 */
    public char attendYn;

    /** 사유 */
    public String reason;

    private static ModelMapper modelMapper = new ModelMapper();

    public static AttendanceMemberDto of(AttendanceMember attendanceMember) {
        return modelMapper.map(attendanceMember, AttendanceMemberDto.class);
    }

    public static AttendanceMember to(AttendanceMemberDto attendanceMemberDto) {
        return modelMapper.map(attendanceMemberDto, AttendanceMember.class);
    }
}
