package bookify.ms.book.core.usecases;

import bookify.ms.book.core.dtos.request.LoginRequestDTO;
import bookify.ms.book.core.exceptions.InvalidEmailPasswordException;
import bookify.ms.book.core.utils.JwtTokenService;
import bookify.ms.book.core.utils.AuthenticationService;

public class LoginUserUseCase {
    private final AuthenticationService authService;
    private final JwtTokenService jwtTokenService;

    public LoginUserUseCase(AuthenticationService authService, JwtTokenService jwtTokenService) {
        this.authService = authService;
        this.jwtTokenService = jwtTokenService;
    }

    public String execute(LoginRequestDTO dto) throws InvalidEmailPasswordException {
        
        if(authService.authenticate(dto.email(), dto.password()) == null) throw new InvalidEmailPasswordException();
        return jwtTokenService.generateToken(dto.email());
    }
}
