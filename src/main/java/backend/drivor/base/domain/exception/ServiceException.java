package backend.drivor.base.domain.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException{

    private String error;
    private String description;
    private HttpStatus httpStatus;

    public ServiceException() {

    }

    public ServiceException(String error, String description, HttpStatus httpStatus) {
        this.error = error;
        this.description = description;
        this.httpStatus = httpStatus;
    }

}
