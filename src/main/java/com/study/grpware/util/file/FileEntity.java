package com.study.grpware.util.file;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity @Table(name = "file")
@Getter @Setter @ToString
public class FileEntity {

    @Id @Column(name = "file_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fileId;

    @Column(name = "file_ori_path")
    private String fileOriPath;

    @Column(name = "file_ori_name")
    private String fileOriName;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "registrant")
    private String registrant;

    @Column(name = "reg_date")
    private String regDate;
}
