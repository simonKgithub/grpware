package com.study.grpware.util.email;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/check")
    @ResponseBody
    public String ajaxWhatEmailCheck(@RequestBody EmailDto emailDto) {
        return emailService.emailCheck(emailDto);
    }
}
