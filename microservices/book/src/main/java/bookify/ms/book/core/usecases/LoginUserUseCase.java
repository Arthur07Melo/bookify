package bookify.ms.book.core.usecases;

import bookify.ms.book.core.dtos.LoginUserDTO;
import bookify.ms.book.core.exceptions.InvalidEmailPasswordException;
import bookify.ms.book.core.utils.JwtTokenService;
import bookify.ms.book.core.utils.ValidateAuthentication;

public class LoginUserUseCase {
    private final ValidateAuthentication validateAuthentication;
    private final JwtTokenService jwtTokenService;

    public LoginUserUseCase(ValidateAuthentication validateAuthentication, JwtTokenService jwtTokenService) {
        this.validateAuthentication = validateAuthentication;
        this.jwtTokenService = jwtTokenService;
    }

    public String execute(LoginUserDTO dto) throws InvalidEmailPasswordException {
        if(validateAuthentication.authenticate(dto.email(), dto.password()) == null) throw new InvalidEmailPasswordException();
        return jwtTokenService.generateToken(dto.email());
    }
}
