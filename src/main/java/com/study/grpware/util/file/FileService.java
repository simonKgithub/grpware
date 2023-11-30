package com.study.grpware.util.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class FileService {

    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception {
        LocalDateTime nowTime = LocalDateTime.now();
        String now = nowTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        String[] fileName = originalFileName.split("\\.");

        String savedFileName = fileName[0] + now + "." + fileName[1];

        String uploadFullUrl = uploadPath + "/" + savedFileName;

        FileOutputStream fos = new FileOutputStream(uploadFullUrl);
        fos.write(fileData);

        fos.close();

        return savedFileName;
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

}
