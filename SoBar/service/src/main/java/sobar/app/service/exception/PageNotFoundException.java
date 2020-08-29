package sobar.app.service.exception;

public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException() {
        super("404 lol");
    }
}
