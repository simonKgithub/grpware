package com.study.grpware.security;

import com.study.grpware.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberService memberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login")        //로그인 페이지
                .defaultSuccessUrl("/")     //로그인 성공 시 이동 url
                .usernameParameter("email") //로그인 시 사용할 파라미터 이름
                .failureHandler(customAuthenticationFailureHandler()) //로그인 실패 시 로그인 실패 url 커스텀
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //get으로 /logout 날려도 시큐리티에서 post로 인식
                .logoutSuccessUrl("/login/logout");    //로그아웃 성공 시 이동 url

        http.authorizeRequests() //시큐리티 처리에 HttpServletRequest 이용
                .mvcMatchers("/login", "/login/error", "/login/logout", "/login/accessDenied",
                        "/member/memberJoin", "/forget", "/email/check",
                        "/css/**", "/js/**", "/images/**", "/img/**", "/media/**", "/webfonts/**").permitAll()
                /*.mvcMatchers("/admin/**").hasRole("ADMIN")*/
                .anyRequest().authenticated();

        http.exceptionHandling() //인증되지 않은 사용자의 리소스 접근 시 수행되는 핸들러
                .authenticationEntryPoint(new GrpAuthenticationEntryPoint());
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        /**
         * 보안 상 권장하지 않아 주석 처리
         * 및 http.authorizeRequests() 메서드 내 permitAll()로 선언
         */
        //webSecurity.ignoring().antMatchers("/css/**", "/js/**", "/images/**");
    }

    /**
     * 암호화
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 인증이 이루어지는 곳, userDetailService 구현체 지정 및 암호화 지정
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * 로그인 오류 시 각 url 지정
     * @return
     */
    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler(){
        return new CustomAuthenticationFailureHandler();
    }
}
