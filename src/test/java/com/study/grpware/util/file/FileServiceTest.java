package com.study.grpware.util.file;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional //테스트에서 선언할 경우 테스트 종료 후 rollback 됨
@TestPropertySource(locations = "classpath:application-test.properties")
class FileServiceTest {

    @Autowired FileService fileService;

    @Test
    @DisplayName("파일 삭제 테스트")
    void fileDeleteTest(){
        String uploadPath = "C:/upload/temp";
        String originalFileName = "파일테스트.png";
        byte[] fileData = {};

        try {
            String savedFileName = fileService.uploadFile(uploadPath, originalFileName, fileData);
            String filePath = uploadPath + "/" + savedFileName;
            fileService.deleteFile(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    @DisplayName("파일 업로드 테스트")
    void fileUploadTest(){
        String uploadPath = "C:/upload/temp";
        String originalFileName = "파일테스트.png";
        byte[] fileData = {};

        try {
            String savedFileName = fileService.uploadFile(uploadPath, originalFileName, fileData);

            assertEquals(savedFileName.substring(0,5), "파일테스트");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}