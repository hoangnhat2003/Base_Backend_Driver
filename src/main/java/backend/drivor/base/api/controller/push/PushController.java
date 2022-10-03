package backend.drivor.base.api.controller.push;

import backend.drivor.base.domain.response.ApiResponse;
import backend.drivor.base.domain.response.FileResponse;
import backend.drivor.base.service.inf.AWSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/push")
public class PushController {

    @Autowired
    private AWSService awsService;

    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadFile(@RequestPart(value = "file") MultipartFile file) {
        FileResponse data = awsService.uploadFile(file);
        ApiResponse<FileResponse> response = new ApiResponse<>();
        response.setCode(HttpStatus.OK.name());
        response.setMessage("Upload file successfully!");
        response.setData(data);
        return ResponseEntity.ok(response);
    }
}
