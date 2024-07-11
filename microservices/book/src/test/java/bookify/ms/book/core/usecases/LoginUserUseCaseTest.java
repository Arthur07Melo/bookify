package bookify.ms.book.core.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import bookify.ms.book.core.domain.User;
import bookify.ms.book.core.dtos.request.LoginRequestDTO;
import bookify.ms.book.core.exceptions.InvalidEmailPasswordException;
import bookify.ms.book.core.utils.AuthenticationService;
import bookify.ms.book.core.utils.JwtTokenService;

@ExtendWith(MockitoExtension.class)
public class LoginUserUseCaseTest {
    @Mock
    AuthenticationService authService;
    @Mock
    JwtTokenService jwtTokenService;
    @InjectMocks
    LoginUserUseCase useCase;

    @Test
    @DisplayName("should login sucessfully when passing valid login data")
    public void testLoginSuccess() throws InvalidEmailPasswordException {
        var dto = new LoginRequestDTO("teste@teste.com", "123@Teste");
        when(authService.authenticate("teste@teste.com", "123@Teste"))
        .thenReturn(new User());

        when(jwtTokenService.generateToken(dto.email()))
        .thenReturn("myAuthenticationToken");

        assertEquals("myAuthenticationToken", useCase.execute(dto));
    }

    @Test
    @DisplayName("should not login when passing invalid email or password")
    public void testLoginInvalidEmailPassword() throws InvalidEmailPasswordException {
        var dto = new LoginRequestDTO("teste@teste.com", "123@Teste");
        when(authService.authenticate("teste@teste.com", "123@Teste"))
        .thenReturn(null);

        assertThrows(InvalidEmailPasswordException.class, () -> useCase.execute(dto));
    }
}
