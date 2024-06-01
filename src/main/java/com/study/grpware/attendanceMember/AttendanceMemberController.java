package com.study.grpware.attendanceMember;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/attendanceMember")
@PreAuthorize("hasRole('ADMIN')")/* Admin 만 접근 가능 */
@RequiredArgsConstructor
public class AttendanceMemberController{

    final private AttendanceMemberService attendanceMemberService;

    /**
     * 화면 이동: 출결 관리
     */
    @GetMapping("/attendanceMemberPage")
    public String attendanceMemberPage(Model model) {
        return "/attendanceMember/attendanceMemberPage";
    }

    /**
     * 출석 저장
     */
    @PostMapping("/saveAttendance")
    public ResponseEntity<String> saveAttendance(AttendanceMemberDto attendanceMemberDto) {

        attendanceMemberService.saveAttendance(attendanceMemberDto);

        return ResponseEntity.ok("출석이 확인되었습니다.");
    }

    /**
     * 결석 저장
     */
    @PostMapping("/saveAbsent")
    public ResponseEntity<String> saveAbsent(AttendanceMemberDto attendanceMemberDto) {

        attendanceMemberService.saveAbsent(attendanceMemberDto);

        return ResponseEntity.ok("결석이 확인되었습니다.");
    }
}
