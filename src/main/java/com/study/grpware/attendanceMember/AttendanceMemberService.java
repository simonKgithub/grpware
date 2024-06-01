package com.study.grpware.attendanceMember;

import java.util.List;

public interface AttendanceMemberService {

    /** 출석을 저장한다. */
    void saveAttendance(AttendanceMemberDto attendanceMemberDto);

    /** 결석을 저장한다. */
    void saveAbsent(AttendanceMemberDto attendanceMemberDto);

    /** 출결 현황을 조회한다 */
    List<AttendanceMemberDto> getAttendanceList();
}
