package bookify.ms.book.infra.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bookify.ms.book.core.domain.User;
import bookify.ms.book.core.dtos.request.LoginRequestDTO;
import bookify.ms.book.core.dtos.request.RegisterRequestDTO;
import bookify.ms.book.core.dtos.response.LoginResponseDTO;
import bookify.ms.book.core.exceptions.EmailAlreadyExistsException;
import bookify.ms.book.core.exceptions.InvalidEmailPasswordException;
import bookify.ms.book.core.usecases.LoginUserUseCase;
import bookify.ms.book.core.usecases.RegisterUserUseCase;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class SecurityController {
    private final LoginUserUseCase loginUserUseCase;
    private final RegisterUserUseCase registerUserUseCase;

    public SecurityController(LoginUserUseCase loginUserUseCase, RegisterUserUseCase registerUserUseCase) {
        this.loginUserUseCase = loginUserUseCase;
        this.registerUserUseCase = registerUserUseCase;
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) throws InvalidEmailPasswordException{
        String token = loginUserUseCase.execute(dto);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequestDTO dto) throws EmailAlreadyExistsException {
        log.info("Started user register");
        var user = registerUserUseCase.execute(dto);
        return ResponseEntity.status(201).body(user);
    }
}
