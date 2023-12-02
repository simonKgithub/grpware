package com.study.grpware.util.file;

import com.study.grpware.member.Member;
import com.study.grpware.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public Long saveFile(FileDto fileDto) {
        FileEntity fileEntity = new FileEntity();
        LocalDateTime nowTime = LocalDateTime.now();
        String now = nowTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        Member member = CommonUtils.getMember();

        fileEntity.setFileName(fileDto.getFileName());
        fileEntity.setFileOriName(fileDto.getFileOriName());
        fileEntity.setFileOriPath(fileDto.getFileOriPath());
        fileEntity.setRegistrant(member.getMemberName());
        fileEntity.setRegDate(now);

        FileEntity saved = fileRepository.save(fileEntity);

        return saved.getFileId();
    }

    public Long uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception {
        String now = CommonUtils.getNow();

        String[] fileName = originalFileName.split("\\.");

        String savedFileName = fileName[0] + "_" + now + "." + fileName[1];

        String uploadFullUrl = uploadPath + "/" + savedFileName;

        FileOutputStream fos = new FileOutputStream(uploadFullUrl);

        fos.write(fileData);

        fos.close();

        //파일 저장
        FileDto fileDto = new FileDto();
        fileDto.setFileOriPath(uploadFullUrl);
        fileDto.setFileOriName(originalFileName);
        fileDto.setFileName(savedFileName);

        return this.saveFile(fileDto);
    }

    public void deleteFile(String filePath) throws Exception {
        File deleteFile = new File(filePath);

        if (deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }

    public FileEntity findByFileId(Long fileId) {
        return fileRepository.findById(fileId).orElseThrow(EntityNotFoundException::new);
    }
}
