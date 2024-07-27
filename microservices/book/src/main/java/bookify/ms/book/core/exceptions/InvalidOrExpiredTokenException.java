package bookify.ms.book.core.exceptions;

public class InvalidOrExpiredTokenException extends BusinessException {

    public InvalidOrExpiredTokenException() {
        super("invalid or expired token");
    }
}
