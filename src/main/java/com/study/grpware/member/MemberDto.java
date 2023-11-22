package com.study.grpware.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Setter @ToString
public class MemberDto {
    @Email(message = "이메일 형식을 맞춰주세요.")
    private String email;

    @NotBlank(message = "*비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "*이름을 입력해주세요.")
    private String memberName;

    @NotBlank(message = "*휴대폰번호를 입력해주세요.")
    private String memberNumber;

    @NotBlank(message = "*생년월일을 입력해주세요.")
    private String memberBirth;

    private static ModelMapper modelMapper = new ModelMapper();

    public Member to(){
        return modelMapper.map(this, Member.class);}

    public static MemberDto of(Member member) {
        return modelMapper.map(member, MemberDto.class);
    }

}
