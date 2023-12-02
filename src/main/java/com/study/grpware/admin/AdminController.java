package com.study.grpware.admin;

import com.study.grpware.announce.AnnouncementDto;
import com.study.grpware.announce.AnnouncementEntity;
import com.study.grpware.announce.AnnouncementService;
import com.study.grpware.util.file.FileDto;
import com.study.grpware.util.file.FileEntity;
import com.study.grpware.util.file.FileService;
import com.study.grpware.util.file.ImageValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/admin")
public class AdminController {

    @Value("${fileUploadLocation}")
    String fileUploadLocation;

    private final FileService fileService;
    private final AnnouncementService announcementService;

    @GetMapping("/adminPage")
    public String goToAdminPage(Model model){
        return "/admin/adminPage";
    }

    @GetMapping("/announceWithPopup")
    public String goToPopupNoticePage(Model model){
        List<AnnouncementEntity> announcementEntityList = announcementService.findAll();
        List<AnnouncementDto> announcementDtoList = new ArrayList<>();
        if (announcementEntityList.size() > 0) {
            announcementEntityList.forEach( entity -> {
                announcementDtoList.add(AnnouncementDto.of(entity));
            });
        }
        model.addAttribute("announcementDtoList", announcementDtoList);
        return "admin/announcementPopupPage";
    }

    /**
     * 이미지 형식 유효성 검증
     * @param file
     * @return
     */
    @PostMapping("/imagesValidation")
    @ResponseBody
    public boolean imagesValidationCheck(@RequestPart("file") MultipartFile file){
        return ImageValidator.isValidImage(file);
    }

    /**
     * 이미지 파일 업로드
     * @param file
     * @return
     */
    @PostMapping("/imagesUpload")
    @ResponseBody
    public ResponseEntity<Long> imagesUpload(@RequestPart("file") MultipartFile file) {
        Long savedFileId = null;
        try {
            savedFileId = fileService.uploadFile(fileUploadLocation, file.getOriginalFilename(), file.getBytes());
        } catch (Exception e) {
            log.error(e.getMessage(), "파일 저장 중 오류가 발생하였습니다.");
        }
        return ResponseEntity.ok(savedFileId);
    }

    /**
     * 이미지 파일 삭제
     */
    @PostMapping("/imagesDelete")
    @ResponseBody
    public void imagesDelete(@RequestBody FileDto fileDto) {
        FileEntity fileEntity = fileService.findByFileId(fileDto.getFileId());
        String fileOriPath = fileEntity.getFileOriPath();

        try {
            fileService.deleteFile(fileOriPath);
        } catch (Exception e) {
            log.error(e.getMessage(), "파일 삭제 중 오류가 발생하였습니다.");
        }
    }
    /**
     * 이미지 미리보기
     */
    @PostMapping("/imagesPreview")
    @ResponseBody
    public FileDto imagesPreview(@RequestBody FileDto fileDto){
        FileEntity fileEntity = fileService.findByFileId(fileDto.getFileId());
        return FileDto.of(fileEntity);
    }

    /**
     * 공지 팝업 등록
     */
    @PostMapping("/annoRegistration")
    public String annoRegistration(@Valid AnnouncementDto announcementDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/admin/announceWithPopup";
        }
        return "redirect:/admin/announceWithPopup";
    }
}
