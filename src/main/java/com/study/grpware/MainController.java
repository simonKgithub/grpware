package com.study.grpware;

import com.study.grpware.announce.AnnouncementDto;
import com.study.grpware.announce.AnnouncementEntity;
import com.study.grpware.announce.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final AnnouncementService announcementService;

    @GetMapping(value = "/")
    public String main(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        //클라이언트에서 넘어온 파라미터 확인(쿠키)
        Cookie[] cookies = request.getCookies();
        List<String> hidePopupArr = new ArrayList<>();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().startsWith("hidePopup")) {
                    hidePopupArr = Arrays.asList(cookie.getValue().split("-"));
                }
            }
        }

        //공지사항 내용 가져오기(기한에만 있는 것)
        List<AnnouncementEntity> announcementEntityList = announcementService.findAllByStartAndEndDate();
        List<AnnouncementDto> announcementDtoList = new ArrayList<>();
        if (announcementEntityList.size() > 0) {
            List<String> finalHidePopupArr = hidePopupArr;
            announcementEntityList.forEach(entity -> {
                // 오늘 하루 안보기 팝업 제외
                if (finalHidePopupArr.size() > 0) {
                    if (!finalHidePopupArr.contains(String.valueOf(entity.getAnnoId()))) {
                        announcementDtoList.add(AnnouncementDto.of(entity));
                    }
                } else {
                    announcementDtoList.add(AnnouncementDto.of(entity));
                }
            });
        }
        model.addAttribute("announcementDtoList", announcementDtoList);

        return "/main";
    }
}
