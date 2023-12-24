package com.study.grpware.announce;

import com.study.grpware.util.CommonUtils;
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
@PreAuthorize("hasRole('ADMIN')")/* Admin 만 접근 가능 */
@RequestMapping(value = "/announcement")
public class AnnouncementController {

    @Value("${fileUploadLocation}")
    String fileUploadLocation;

    private final FileService fileService;
    private final AnnouncementService announcementService;

    /**
     * 공지사항 팝업 화면 띄우기
     * @param annoId
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")/* Admin, USER 접근 가능 */
    @GetMapping("/popup/{annoId}")
    public String openPopup(@PathVariable Long annoId, Model model) {
        AnnouncementEntity annoEntity = announcementService.findById(annoId);
        // 첨부파일 처리
        if (annoEntity.getFileEntity() != null) {
            FileEntity fileEntity = fileService.findByFileId(annoEntity.getFileEntity().getFileId());
            //첨부파일도 모델에 넣도록 조치를 취해야 한다.(DB연결 후 첨부파일 관리 시 실행)
        }
        AnnouncementDto announcementDto = AnnouncementDto.of(annoEntity);
        model.addAttribute("announcementDto", announcementDto);

        return "/announcement/announcePopup";
    }
    /**
     * 공지사항 삭제
     * @param announcementDto
     */
    @PostMapping("/deleteAnnouncement")
    @ResponseBody
    public ResponseEntity<String> deleteAnnouncement(@RequestBody AnnouncementDto announcementDto) {
        return announcementService.deleteAnnouncement(announcementDto);
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
        //날짜 형식 변경 (2023-12-12 > 20231212)
        announcementDto.setEndDate(announcementDto.getEndDate().replaceAll("-", ""));
        announcementDto.setStartDate(announcementDto.getStartDate().replaceAll("-", ""));

        announcementService.addAnnouncementPopup(announcementDto);
        response.put("success", true);
        response.put("redirectUrl", "/announcement/announceWithPopup");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 공지사항 관리 화면 이동
     * @param model
     * @return
     */
    @GetMapping("/announceWithPopup")
    public String goToAnnouncementPage(Model model){
        //공지사항 내용 가져오기
        List<AnnouncementEntity> announcementEntityList = announcementService.findAllByOrderByRegDateDesc();
        List<AnnouncementDto> announcementDtoList = new ArrayList<>();
        if (announcementEntityList.size() > 0) {
            announcementEntityList.forEach( entity -> {
                AnnouncementDto dto = AnnouncementDto.of(entity);
                // 날짜 변환 후 넣어주기
                dto.setRegDate(CommonUtils.changeDateFormat(dto.getRegDate()));
                dto.setStartDate(CommonUtils.changeDateFormat(dto.getStartDate()));
                dto.setEndDate(CommonUtils.changeDateFormat(dto.getEndDate()));
                announcementDtoList.add(dto);
            });
        }
        model.addAttribute("announcementDtoList", announcementDtoList);
        return "announcement/announcementPopupPage";
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
