package sobar.app.service.exception;

public class InvalidParamFormatException extends RuntimeException {
    public InvalidParamFormatException() {
        super("Invalid parameter format");
    }
}
