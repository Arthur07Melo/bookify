package bookify.ms.book.core.exceptions;

public class EmailAlreadyExistsException extends BusinessException {
    public EmailAlreadyExistsException() {
        super("email is already registered");
    }
}
