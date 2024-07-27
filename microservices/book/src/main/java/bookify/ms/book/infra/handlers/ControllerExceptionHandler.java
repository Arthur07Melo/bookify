package bookify.ms.book.infra.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import bookify.ms.book.core.exceptions.BusinessException;
import bookify.ms.book.core.exceptions.InvalidOrExpiredTokenException;


@ControllerAdvice
public class ControllerExceptionHandler {
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorDTO> exceptionHandler(Exception ex) {
        return ResponseEntity.badRequest().body(new ErrorDTO(ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDTO> badCredentialsExceptionHandler(){
        return ResponseEntity.status(401).body(new ErrorDTO("email or password is invalid"));
    }

    @ExceptionHandler(InvalidOrExpiredTokenException.class)
    public ResponseEntity<ErrorDTO> invalidOrExpiredTokenExceptionHandler(){
        return ResponseEntity.status(401).body(new ErrorDTO("invalid or expired token"));
    }

    
}
