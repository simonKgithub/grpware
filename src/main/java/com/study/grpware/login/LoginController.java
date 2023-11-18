package com.study.grpware.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String goToLoginPage(){
        return "login/login";
    }
}
