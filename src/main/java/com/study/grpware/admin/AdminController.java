package com.study.grpware.admin;

import com.study.grpware.util.file.ImageValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

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

    /**
     * 이미지 형식 유효성 검증
     * @param file
     * @return
     */
    @PostMapping("imagesValidation")
    @ResponseBody
    public boolean imagesValidationCheck(@RequestPart("file") MultipartFile file){
        return ImageValidator.isValidImage(file);
    }
}
