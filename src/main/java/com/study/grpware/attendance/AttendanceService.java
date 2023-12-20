package com.study.grpware.attendance;

public interface AttendanceService {

    /**
     * 저장: 대표 스터디 장소
     * @param attendanceDto
     * @return
     */
    AttendanceEntity saveRepPlace(AttendanceDto attendanceDto);

    /**
     * 조회: 대표 스터디 장소
     * @return
     */
    AttendanceEntity findRepPlace(boolean repYn);
}
