package com.study.grpware.attendanceMember;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceMemberServiceImpl implements AttendanceMemberService{

    private final AttendanceMemberRepository attendanceMemberRepository;

    @Override
    public void saveAttendance(AttendanceMemberDto attendanceMemberDto) {
        attendanceMemberRepository.save(AttendanceMemberDto.to(attendanceMemberDto));
    }

    @Override
    public void saveAbsent(AttendanceMemberDto attendanceMemberDto) {
        attendanceMemberRepository.save(AttendanceMemberDto.to(attendanceMemberDto));
    }

    @Override
    public List<AttendanceMemberDto> getAttendanceList() {
        return null;
    }
}
