package com.study.grpware.attendance;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/attend")
@PreAuthorize("hasRole('ADMIN')")/* Admin 만 접근 가능 */
@RequiredArgsConstructor
public class AttendanceController {

    final private AttendanceService attendanceService;

    /**
     * 출석 체크: 출석 지정 범위 약 70m 이내에만 유효함
     */
    @PostMapping("/attendCheck")
    @ResponseBody
    public ResponseEntity<String> attendCheck(@RequestBody AttendanceDto attendanceDto) {
        try {
            attendanceService.attendCheck(attendanceDto);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("출석체크가 완료되었습니다.", HttpStatus.OK);
    }

    /**
     * 저장: 대표 스터디 장소
     * @param attendanceDto
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping("/saveRepPlace")
    public String saveRepPlace(@Valid AttendanceDto attendanceDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "attendance/attendanceManagePage";
        }
        AttendanceEntity entity = attendanceService.saveRepPlace(attendanceDto);

        model.addAttribute("attendanceDto", AttendanceDto.of(entity));

        return "redirect:/attend/attendManage";
    }

    /**
     * 화면 이동: 회원 출석 관리
     * @return
     */
    @GetMapping("/attendManage")
    public String goToAttendanceManagePage(Model model){
        AttendanceEntity repPlace = attendanceService.findRepPlace(true);

        if (repPlace == null) {
            model.addAttribute("attendanceDto", new AttendanceDto());
        }else {
            model.addAttribute("attendanceDto", AttendanceDto.of(repPlace));
        }
        return "attendance/attendanceManagePage";
    }
}
