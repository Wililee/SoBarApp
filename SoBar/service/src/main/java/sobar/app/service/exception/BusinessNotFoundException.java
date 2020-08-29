package sobar.app.service.exception;

public class BusinessNotFoundException extends RuntimeException {
    public BusinessNotFoundException(Long id) {
        super("Could not find business " + id);
    }
}