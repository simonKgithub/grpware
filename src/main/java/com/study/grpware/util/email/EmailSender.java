package com.study.grpware.util.email;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

@Getter @Setter @Slf4j
public class EmailSender {
    public EmailSender(String emailAddress, String emailPassword, String senderName) {
        this.emailAddress = emailAddress;
        this.emailPassword = emailPassword;
        this.senderName = senderName;
    }
    private String emailAddress;
    private String emailPassword;
    private String senderName;

    public void sendEmail(String email, String subject, String msg) {
        SimpleEmail mail = new SimpleEmail();
        mail.setCharset("utf-8");//한글깨지지 않게 인코딩
        mail.setDebug(false);//전송과정 로그 출력
        mail.setHostName("smtp.naver.com");//호스트의 smtp 주소 입력
        mail.setSmtpPort(465);
        mail.setAuthentication(emailAddress, emailPassword); //해당 네이버 아이디 로그인
        mail.setSSLOnConnect(true);

        try {
            mail.setFrom(emailAddress, senderName);//보내는 사람 이메일 주소와 이름
            mail.addTo(email); //받을 사람
            mail.setSubject(subject);//주제
            mail.setMsg(msg);
            mail.send();
        } catch (EmailException e) {
            log.error("이메일 발송 도중 오류 발생", e);
        }

    }
}
