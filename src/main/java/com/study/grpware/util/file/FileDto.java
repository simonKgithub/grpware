package com.study.grpware.util.file;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Getter @Setter @ToString
public class FileDto {

    private Long fileId;

    private String fileOriPath;

    private String fileOriName;

    private String fileName;

    private static ModelMapper modelMapper = new ModelMapper();

    public static FileDto of(FileEntity fileEntity) {
        return modelMapper.map(fileEntity, FileDto.class);
    }
}
