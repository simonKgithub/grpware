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

    /**
     * 저장: 사용자 출석 체크
     * @param attendanceDto
     */
    void attendCheck(AttendanceDto attendanceDto);

    /**
     * 현재 접속한 회원이 현재 날짜에 출석 체크를 했는지 확인
     * @param
     * @return
     */
    boolean isAttend();
}
