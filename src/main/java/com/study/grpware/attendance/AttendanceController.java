package com.study.grpware.attendance;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/attend")
@PreAuthorize("hasRole('ADMIN')")/* Admin 만 접근 가능 */
@RequiredArgsConstructor
public class AttendanceController {

    final private AttendanceService attendanceService;

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

        model.addAttribute("attendanceDto", AttendanceDto.of(repPlace));

        return "attendance/attendanceManagePage";
    }
}
