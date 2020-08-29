package sobar.app.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BusinessNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(BusinessNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String businessNotFoundHandler(BusinessNotFoundException ex) {
        return ex.getMessage();
    }
}
