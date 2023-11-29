package com.study.grpware.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/admin")
public class AdminController {

    @GetMapping("/adminPage")
    public String goToAdminPage(Model model){
        return "/admin/adminPage";
    }

    @GetMapping("/announceWithPopup")
    public String goToPopupNoticePage(){
        return "/admin/popupSettingPage";
    }
}
