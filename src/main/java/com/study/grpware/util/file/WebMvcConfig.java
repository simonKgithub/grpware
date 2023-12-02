package com.study.grpware.util.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${uploadPath}")
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * 정적 자원으로 들어오는 요청을 uploadPath 경로로 매핑함
         * 여기서는 /images/** 로 들어오는 요청을
         * uploadPath 경로로 매핑해버림
         */
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);

        /**
         * 파비콘도 처리해줌 (images로 들어가서 반응되지 않아 조치를 취함)

        registry.addResourceHandler("/favicon.svg")
                .addResourceLocations(uploadPath);
         */
    }
}
