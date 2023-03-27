package sixman.helfit.domain.file.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sixman.helfit.domain.file.service.FileService;
import sixman.helfit.exception.BusinessLogicException;
import sixman.helfit.exception.ExceptionCode;
import sixman.helfit.response.ApiResponse;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    /*
     * # 파일업로드 (단일)
     *
     */
    @PostMapping(value = "/upload")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> upload(@RequestParam MultipartFile multipartFile) throws Exception {
        if (multipartFile.isEmpty())
            throw new BusinessLogicException(ExceptionCode.INVALID_INPUT_VALUE);

        String imagePath = fileService.uploadFile(multipartFile);

        return ResponseEntity.ok().body(ApiResponse.ok("resource", imagePath));
    }

    /*
     * # 파일업로드 (멀티)
     *
     */
    @PostMapping(value = "/multi-upload")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> multiUpload(@RequestParam MultipartFile[] multipartFiles) throws Exception {
        if (Arrays.stream(multipartFiles).anyMatch(MultipartFile::isEmpty))
            throw new BusinessLogicException(ExceptionCode.INVALID_INPUT_VALUE);

        List<String> imagePathList = fileService.uploadFiles(multipartFiles);

        return ResponseEntity.ok().body(ApiResponse.ok("resource", imagePathList));
    }
}
