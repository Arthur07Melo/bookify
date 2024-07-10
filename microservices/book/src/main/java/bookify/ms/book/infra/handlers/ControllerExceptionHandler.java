package bookify.ms.book.infra.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import bookify.ms.book.core.exceptions.BusinessException;


@ControllerAdvice
public class ControllerExceptionHandler {
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorDTO> exceptionHandler(Exception ex) {
        return ResponseEntity.badRequest().body(new ErrorDTO(ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDTO> badCredentialsExceptionHandler(){
        return ResponseEntity.status(403).body(new ErrorDTO("email or password is invalid"));
    }

}
