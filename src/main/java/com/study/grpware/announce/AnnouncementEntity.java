package com.study.grpware.announce;

import com.study.grpware.util.file.FileEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity @Table(name = "announcement")
@Getter @Setter @ToString
public class AnnouncementEntity {
    @Id @Column(name = "anno_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long annoId;

    @Column(name = "anno_title")
    private String annoTitle;

    @Column(name = "anno_contents")
    @Lob
    private String annoContents;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private FileEntity fileEntity;

    @Column(name = "registrant")
    private String registrant;

    @Column(name = "reg_date")
    private String regDate;
}
