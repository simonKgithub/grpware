package com.study.grpware.util;

import com.study.grpware.member.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommonUtils {

    /**
     * 현재 로그인한(인증된) 사용자를 반환한다.
     * @return Member.class
     */
    public static Member getMember(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !"anonymousUser".equals(authentication.getName())) {
            return (Member)authentication.getPrincipal();
        } else {
            return null;
        }
    }

    /**
     * 현재 시간을 년월일시분초(yyyyMMddHHmmss)형식으로 반환한다.
     * @return String
     */
    public static String getNow(){
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    /**
     * 날짜를 뷰에 보기 좋도록 변경해준다.
     * @param date(yyyyMMddHImmdd)
     * @return
     */
    public static String changeDateFormat(String date) {
        String yyyy = date.substring(0, 4);
        String mm = date.substring(4, 6);
        String dd = date.substring(6, 8);
        return yyyy + "." + mm + "." + dd;
    }
}
