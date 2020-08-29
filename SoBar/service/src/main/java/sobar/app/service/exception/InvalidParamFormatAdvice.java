package sobar.app.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidParamFormatAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidParamFormatException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public String invalidParamFormatHandler(InvalidParamFormatException ex) {
        return ex.getMessage();
    }
}
