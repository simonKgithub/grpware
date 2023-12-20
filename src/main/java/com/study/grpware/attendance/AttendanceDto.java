package com.study.grpware.attendance;

import com.study.grpware.member.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;

@Getter @Setter @ToString
public class AttendanceDto {
    public Long attendId;

    @NotBlank(message = "*위도 입력 필요")
    public String lat;
    @NotBlank(message = "*경도 입력 필요")
    public String lng;
    @NotBlank(message = "*장소명 입력 필요")
    public String placeName;
    @NotBlank(message = "*주소 입력 필요")
    public String address;

    public boolean repYn;
    public String regDate;
    public Member member;

    private static ModelMapper modelMapper = new ModelMapper();

    public static AttendanceDto of(AttendanceEntity attendanceEntity) {
        return modelMapper.map(attendanceEntity, AttendanceDto.class);
    }
}
