package backend.drivor.base.service.inf;

import backend.drivor.base.domain.response.FileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface AWSService {
    FileResponse uploadFile(MultipartFile multipartFile);
}
