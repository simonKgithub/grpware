package com.study.grpware.member;

import com.study.grpware.constant.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter @Setter @ToString
public class MemberFormDto {
    @NotBlank(message = "*입력필요")
    private String emailId;

    @NotBlank(message = "*입력필요")
    private String emailAddress;

    @NotBlank(message = "*비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "*이름을 입력해주세요.")
    private String memberName;

    @NotBlank(message = "*휴대폰번호를 입력해주세요.")
    private String memberNumber;

    @NotBlank(message = "*입력필요")
    @Length(max = 4)
    private String birthYYYY;

    @NotBlank(message = "*입력필요")
    @Length(max=2)
    private String birthMM;

    @NotBlank(message = "*입력필요")
    @Length(max=2)
    private String birthDD;

    private Role role;

    public static MemberFormDto of(Member member) {
        MemberFormDto memberFormDto = new MemberFormDto();
        String email = member.getEmail();
        String birth = member.getMemberBirth();
        String[] emailArr = email.split("@");
        String[] birthArr = birth.split("-");
        memberFormDto.setEmailId(emailArr[0]);
        memberFormDto.setEmailAddress("@"+emailArr[1]);
        memberFormDto.setPassword(member.getPassword());
        memberFormDto.setMemberName(member.getMemberName());
        memberFormDto.setMemberNumber(member.getMemberNumber());
        memberFormDto.setBirthYYYY(birthArr[0]);
        memberFormDto.setBirthMM(birthArr[1]);
        memberFormDto.setBirthDD(birthArr[2]);
        return memberFormDto;
    }
}
