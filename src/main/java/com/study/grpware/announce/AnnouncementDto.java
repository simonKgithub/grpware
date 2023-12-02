package com.study.grpware.announce;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;

@Getter @Setter @ToString
public class AnnouncementDto {
    private Long annoId;

    @NotBlank(message = "제목은 필수입니다.")
    private String annoTitle;

    @NotBlank(message = "내용은 필수입니다.")
    private String annoContents;

    @NotBlank(message = "시작일은 필수입니다.")
    @Length(min = 8, max = 8, message = "8자리로 입력해주세요")
    private String startDate;

    @NotBlank(message = "종료일은 필수입니다.")
    @Length(min = 8, max = 8, message = "8자리로 입력해주세요")
    private String endDate;

    private Long fileId;

    private String registrant;

    private String regDate;

    private static ModelMapper modelMapper = new ModelMapper();

    public static AnnouncementDto of(AnnouncementEntity announcementEntity) {
        return modelMapper.map(announcementEntity, AnnouncementDto.class);
    }
}
