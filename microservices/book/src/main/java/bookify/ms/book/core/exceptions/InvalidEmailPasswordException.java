package bookify.ms.book.core.exceptions;

public class InvalidEmailPasswordException extends BusinessException {
    public InvalidEmailPasswordException() {
        super("email or password invalid");
    }
}
