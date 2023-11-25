package com.study.grpware.util;

import com.study.grpware.member.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CommonUtils {

    public static Member getMember(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !"anonymousUser".equals(authentication.getName())) {
            return (Member)authentication.getPrincipal();
        } else {
            return null;
        }
    }
}
