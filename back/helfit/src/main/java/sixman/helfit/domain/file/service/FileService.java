package sixman.helfit.domain.file.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    @Value("${cloud.aws.s3bucket}")
    private String S3Bucket;

    private final AmazonS3Client amazonS3Client;

    public String uploadFile(MultipartFile multipartFile) throws Exception {
        return uploadToS3(multipartFile);
    }

    public List<String> uploadFiles(MultipartFile[] multipartFiles) throws Exception {
        List<String> imagePathList = new ArrayList<>();

        for (MultipartFile multipartFile: multipartFiles) {
            String imagePath = uploadToS3(multipartFile);
            imagePathList.add(imagePath);
        }

        return imagePathList;
    }

    private String uploadToS3(MultipartFile multipartFile) throws IOException {
        String originalName = multipartFile.getOriginalFilename();
        long size = multipartFile.getSize();

        ObjectMetadata objectMetaData = new ObjectMetadata();

        // # 파일명
        objectMetaData.setContentType(multipartFile.getContentType());
        // # 파일 사이즈
        objectMetaData.setContentLength(size);

        // # S3 버킷 업로드
        amazonS3Client.putObject(
            new PutObjectRequest(S3Bucket, originalName, multipartFile.getInputStream(), objectMetaData)
                .withCannedAcl(CannedAccessControlList.PublicRead)
        );

        // ~ 저장 후 생성된 접근 가능 URL 내보내기
        return amazonS3Client.getUrl(S3Bucket, originalName).toString();
    }
}
