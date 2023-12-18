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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "/announcement")
public class AnnouncementController {

    @Value("${fileUploadLocation}")
    String fileUploadLocation;

    private final FileService fileService;
    private final AnnouncementService announcementService;

    @GetMapping("/announceWithPopup")
    public String goToAnnouncementPage(Model model){
        //공지사항 내용 가져오기
        List<AnnouncementEntity> announcementEntityList = announcementService.findAll();
        List<AnnouncementDto> announcementDtoList = new ArrayList<>();
        if (announcementEntityList.size() > 0) {
            announcementEntityList.forEach( entity -> {
                announcementDtoList.add(AnnouncementDto.of(entity));
            });
        }
        model.addAttribute("announcementDtoList", announcementDtoList);
        return "announcement/announcementPopupPage";
    }

    /**
     * 공지팝업삭제
     * @param announcementDto
     */
    @PostMapping("/deleteAnnouncement")
    @ResponseBody
    public ResponseEntity<String> deleteAnnouncement(@RequestBody AnnouncementDto announcementDto) {

        return null;
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
    @ResponseBody
    public ResponseEntity<Map<String, Object>> annoRegistration(@Valid AnnouncementDto announcementDto, BindingResult bindingResult, Model model) {
        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            response.put("success", false);
            response.put("errors", getErrors(bindingResult)); //에러 반환
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        announcementService.addAnnouncementPopup(announcementDto);
        response.put("success", true);
        response.put("redirectUrl", "/announcement/announceWithPopup");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private List<Map<String, Object>> getErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(error -> {
                    Map<String, Object> errorMap = new HashMap<>();
                    errorMap.put("errorField", error.getField());
                    errorMap.put("errorMsg", error.getDefaultMessage());
                    return errorMap;
                })
                .collect(Collectors.toList());
    }
}
