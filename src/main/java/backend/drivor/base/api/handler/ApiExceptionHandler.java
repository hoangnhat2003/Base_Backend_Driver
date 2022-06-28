package backend.drivor.base.api.handler;

import backend.drivor.base.domain.handler.ServiceExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice()
public class ApiExceptionHandler extends ServiceExceptionHandler {
}
