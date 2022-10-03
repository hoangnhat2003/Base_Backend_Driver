package backend.drivor.base.service.impl;

import backend.drivor.base.domain.response.FileResponse;
import backend.drivor.base.service.inf.AWSService;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class AWSServiceImpl implements AWSService {

    @Autowired
    private AmazonS3Client s3client;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    @Override
    public FileResponse uploadFile(MultipartFile multipartFile) {
        try {
            String fileUrl = "";
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            uploadFileTos3bucket(fileName, file);
            fileUrl = s3client.getResourceUrl(bucketName, fileName);
            FileResponse response = new FileResponse(fileName, fileUrl);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new FileResponse();
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
