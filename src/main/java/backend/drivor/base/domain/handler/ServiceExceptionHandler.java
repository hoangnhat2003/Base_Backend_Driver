package backend.drivor.base.domain.handler;

import backend.drivor.base.domain.exception.TokenRefreshException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServiceExceptionHandler {
    @ExceptionHandler({ TokenRefreshException.class })
    protected Object handleRefreshTokenServiceException(HttpServletRequest req, HttpServletResponse res,
                                                 final TokenRefreshException ex) throws IOException {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.OK;
        String response = ex.generateStringResponse();
        return new ResponseEntity<>(response, header, httpStatus);
    }
}
