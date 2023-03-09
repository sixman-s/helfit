package sixman.helfit.domain.file.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sixman.helfit.domain.file.service.FileService;
import sixman.helfit.response.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @GetMapping("/upload")
    public ResponseEntity<Object> upload(MultipartFile[] multipartFileList) throws Exception {
        List<String> imagePathList = fileService.uploadFiles(multipartFileList);

        return ResponseEntity.ok().body(ApiResponse.ok("resource", imagePathList));
    }
}
