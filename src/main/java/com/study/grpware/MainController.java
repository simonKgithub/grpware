package com.study.grpware;

import com.study.grpware.announce.AnnouncementDto;
import com.study.grpware.announce.AnnouncementEntity;
import com.study.grpware.announce.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final AnnouncementService announcementService;

    @GetMapping(value = "/")
    public String main(Model model){
        //공지사항 내용 가져오기
        List<AnnouncementEntity> announcementEntityList = announcementService.findAll();
        List<AnnouncementDto> announcementDtoList = new ArrayList<>();
        if (announcementEntityList.size() > 0) {
            announcementEntityList.forEach( entity -> {
                announcementDtoList.add(AnnouncementDto.of(entity));
            });
        }
        model.addAttribute("announcementDtoList", announcementDtoList);

        return "/main";
    }
}
