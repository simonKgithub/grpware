package com.study.grpware.util.email;

import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class EmailService {

    public String emailCheck(EmailDto emailDto) {
        //참고블로그: https://blog.naver.com/indoubt/222901617791
        String receiver = emailDto.getEmailId() + emailDto.getEmailAddress();
        EmailSender sender = new EmailSender(EmailAccount.ADDRESS, EmailAccount.PASSWORD, "스터디 그룹웨어");

        Random random = new Random();
        String code = String.valueOf(random.nextInt(90000) + 10000);// 10000 이상 99999 이하 난수

        String subject = "이메일 인증 확인 코드";
        String msg = "안녕하세요. 스터디 그룹웨어 회원 가입 인증 메일입니다." +
                "\n아래 인증확인 코드를 사용하여 가입을 진행해주세요." +
                "\n인증 확인 코드: " + code;

        sender.sendEmail(receiver, subject, msg);

        return code;
    }
}
