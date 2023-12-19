package com.study.grpware.member;

import com.study.grpware.constant.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
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

    @NotBlank(message = "*연락처를 입력해주세요.")
    @Length(max = 11, message = "*10자리/11자리 수를 입력해주세요.")
    private String memberNumber;

    @NotBlank(message = "*생년월일을 입력해주세요.")
    @Length(max = 8, message = "*8자리 수를 입력해주세요.")
    private String memberBirth;

    private Role role;

    private boolean enabled;

    private static ModelMapper modelMapper = new ModelMapper();

    public static MemberDto of(Member member) {
        return modelMapper.map(member, MemberDto.class);
    }
}
