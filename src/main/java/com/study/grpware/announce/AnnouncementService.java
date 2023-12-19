package com.study.grpware.announce;

import com.study.grpware.util.CommonUtils;
import com.study.grpware.util.file.FileEntity;
import com.study.grpware.util.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final FileService fileService;

    public List<AnnouncementEntity> findAllByOrderByRegDateDesc(){
        return announcementRepository.findAllByOrderByRegDateDesc();
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

    /**
     * 공지사항 삭제
     * @param announcementDto
     * @return
     */
    public ResponseEntity<String> deleteAnnouncement(AnnouncementDto announcementDto) {
        AnnouncementEntity entity = announcementRepository.findById(announcementDto.getAnnoId()).orElseThrow(EntityNotFoundException::new);

        //파일 존재 시 파일도 함께 삭제
        FileEntity fileEntity = entity.getFileEntity();
        if (fileEntity != null) {
            FileEntity byFileId = fileService.findByFileId(fileEntity.getFileId());
            try {
                fileService.deleteFile(byFileId.getFileOriPath());
            } catch (Exception e) {
                return new ResponseEntity<>("공지사항 삭제 중 오류(파일부분)가 발생하였습니다.", HttpStatus.NOT_IMPLEMENTED);
            }
        }
        announcementRepository.delete(entity);
        return new ResponseEntity<>("해당 공지사항이 삭제되었습니다.",HttpStatus.OK);
    }

    /**
     * 조회(단건): 공지 사항
     * @param annoId
     * @return
     */
    public AnnouncementEntity findById(Long annoId) {
        return announcementRepository.findById(annoId).orElseThrow(EntityNotFoundException::new);
    }

    /**
     * 조회(다건): 공지 사항
     * 조건: 설정 날짜 사이에 있는 공지사항만 표출
     * @return
     */
    public List<AnnouncementEntity> findAllByStartAndEndDate() {
        String yyyymmdd = CommonUtils.getNow().substring(0, 8);
        return announcementRepository.findAllByStartAndEndDate(yyyymmdd);
    }
}
