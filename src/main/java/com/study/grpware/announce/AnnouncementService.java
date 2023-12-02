package com.study.grpware.announce;

import com.study.grpware.util.CommonUtils;
import com.study.grpware.util.file.FileEntity;
import com.study.grpware.util.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final FileService fileService;

    public List<AnnouncementEntity> findAll(){
        return announcementRepository.findAll();
    }

    public AnnouncementEntity addAnnouncementPopup(AnnouncementDto announcementDto) {
        AnnouncementEntity entity = new AnnouncementEntity();
        entity.setAnnoTitle(announcementDto.getAnnoTitle());
        entity.setAnnoContents(announcementDto.getAnnoContents());
        entity.setStartDate(announcementDto.getStartDate());
        entity.setEndDate(announcementDto.getEndDate());
        if (announcementDto.getFileId() != null) {
            FileEntity fileEntity = fileService.findByFileId(announcementDto.getFileId());
            entity.setFileEntity(fileEntity);
        }
        entity.setRegistrant(CommonUtils.getMember().getMemberName());
        entity.setRegDate(CommonUtils.getNow());

        return announcementRepository.save(entity);
    }
}
