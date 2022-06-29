package backend.drivor.base.domain.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ServiceException extends RuntimeException{

    private String error;
    private HttpStatus httpStatus;

    public ServiceException() {

    }

    public ServiceException(String error,  HttpStatus httpStatus) {
        this.error = error;
        this.httpStatus = httpStatus;
    }

    public String generateStringResponse() {
        return String.format("Error: %s, Status code : %s", error, httpStatus.name());
    }

}
