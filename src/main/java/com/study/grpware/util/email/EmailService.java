package com.study.grpware.util.email;

import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class EmailService {
    /**
     * 참고블로그: https://blog.naver.com/indoubt/222901617791
     */
    public String emailCheck(EmailDto emailDto) {
        String code = this.codeGenerate();

        String senderName = "스터디 | 회원가입";
        String receiver = emailDto.getEmailId() + emailDto.getEmailAddress();
        String subject = "이메일 인증 확인 코드";
        String msg = "안녕하세요. 스터디 그룹웨어 회원 가입 인증 메일입니다." +
                "\n\n아래 인증확인 코드를 사용하여 가입을 진행해주세요." +
                "\n\n인증 확인 코드: [" + code + "]";

        this.sendEmailForm(senderName, receiver, subject, msg);

        return code;
    }

    public String emailPassword(String receiver) {
        String code = this.codeGenerate();
        String senderName = "스터디 | 비밀번호변경";
        String subject = "그룹웨어 임시 비밀번호 발급";
        String msg = "안녕하세요. 스터디 그룹웨어 임시 비밀번호 발급 메일입니다." +
                "\n\n아래 임시 비밀번호를 사용하여 로그인을 진행해주세요." +
                "\n\n로그인 후에 반드시 비밀번호를 변경해주세요." +
                "\n\n임시 비밀번호: [" + code + "]";

        this.sendEmailForm(senderName, receiver, subject, msg);

        return code;
    }

    /**
     * 5자리 코드 생성기
     * @return 5자리 code
     */
    private String codeGenerate(){
        Random random = new Random();
        String code = String.valueOf(random.nextInt(90000) + 10000);// 10000 이상 99999 이하 난수
        return code;
    }

    /**
     * 이메일 보내기
     * @param senderName 발신자 이름
     * @param receiver 수신자 이름
     * @param subject 제목
     * @param msg 내용
     */
    private static void sendEmailForm(String senderName, String receiver, String subject, String msg){
        EmailSender sender = new EmailSender(EmailAccount.ADDRESS, EmailAccount.PASSWORD, senderName);
        sender.sendEmail(receiver, subject, msg);
    }
}
