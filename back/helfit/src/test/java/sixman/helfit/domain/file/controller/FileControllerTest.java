package sixman.helfit.domain.file.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;
import sixman.helfit.domain.file.service.FileService;
import sixman.helfit.restdocs.ControllerTest;
import sixman.helfit.restdocs.annotations.WithMockUserCustom;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FileController.class)
class FileControllerTest extends ControllerTest {
    final String DEFAULT_URL = "/api/v1/file";

    @MockBean
    FileService fileService;

    @Test
    @DisplayName("[테스트] 파일 업로드(단일)")
    @WithMockUserCustom
    void uploadTest() throws Exception {
        MultipartFile multipartFile = new MockMultipartFile(
            "multipartFile",
            "file.jpg",
            MediaType.IMAGE_JPEG_VALUE,
            "file".getBytes()
        );

        given(fileService.uploadFile(any(MultipartFile.class)))
            .willReturn("://ObjectStorage-" + multipartFile.getOriginalFilename());

        fileResource(DEFAULT_URL + "/upload", multipartFile)
            .andExpect(status().isOk())
            .andDo(restDocs.document(
                requestParts(
                    partWithName("multipartFile").description("업로드 파일")
                ),
                responseFields(
                    beneathPath("body").withSubsectionId("body"),
                    fieldWithPath("resource").type(JsonFieldType.STRING).description("파일 업로드 경로")
                )
            ));
    }

    @Test
    @DisplayName("[테스트] 파일 업로드(멀티)")
    @WithMockUserCustom
    void multiUploadTest() throws Exception {
        MultipartFile multipartFile1 = new MockMultipartFile(
            "multipartFiles",
            "file1.jpg",
            MediaType.IMAGE_JPEG_VALUE,
            "file1".getBytes()
        );

        MultipartFile multipartFile2 = new MockMultipartFile(
            "multipartFiles",
            "file2.jpg",
            MediaType.IMAGE_JPEG_VALUE,
            "file2".getBytes()
        );

        given(fileService.uploadFiles(any(MultipartFile[].class)))
            .willReturn(
                List.of(
                    "://ObjectStorage-" + multipartFile1.getOriginalFilename(),
                    "://ObjectStorage-" + multipartFile2.getOriginalFilename()
                )
            );

        fileResources(DEFAULT_URL + "/multi-upload", new MultipartFile[] {multipartFile1, multipartFile2})
            .andExpect(status().isOk())
            .andDo(restDocs.document(
                requestParts(
                    partWithName("multipartFiles").description("업로드 파일목록")
                ),
                responseFields(
                    beneathPath("body").withSubsectionId("body"),
                    fieldWithPath("resource[]").type(JsonFieldType.ARRAY).description("파일 업로드 경로")
                )
            ));
    }
}
