package bookify.ms.book.infra.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import bookify.ms.book.core.dtos.request.LoginRequestDTO;
import bookify.ms.book.core.dtos.request.RegisterRequestDTO;
import bookify.ms.book.core.dtos.response.LoginResponseDTO;
import bookify.ms.book.core.exceptions.EmailAlreadyExistsException;
import bookify.ms.book.core.exceptions.InvalidEmailPasswordException;
import bookify.ms.book.core.usecases.LoginUserUseCase;
import bookify.ms.book.core.usecases.RegisterUserUseCase;
import bookify.ms.book.core.domain.Role;
import bookify.ms.book.core.domain.User;

@ExtendWith(MockitoExtension.class)
public class SecurityControllerTest {
    @Mock
    private LoginUserUseCase loginUserUseCase;
    @Mock
    private RegisterUserUseCase registerUserUseCase;
    
    @InjectMocks
    private SecurityController controller;

    @Test
    @DisplayName("Should login user with success")
    public void testSuccessLogin() throws InvalidEmailPasswordException {
        var dto = new LoginRequestDTO("tester@test.com", "123@Test");
        String token = "my_access_token";
        when(loginUserUseCase.execute(dto)).thenReturn(token);

        var expectedDto = new LoginResponseDTO(token);
        var response = controller.login(dto);
        assertEquals(expectedDto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Should not login when email or password are invalid")
    public void testFailedLogin() throws InvalidEmailPasswordException {
        var dto = new LoginRequestDTO("tester@test.com", "invalidpassword");
        when(loginUserUseCase.execute(dto)).thenThrow(InvalidEmailPasswordException.class);

        assertThrows(InvalidEmailPasswordException.class, () -> controller.login(dto));
    }

    @Test
    @DisplayName("Should register user with succcess when passing valid parameters")
    public void testSuccessRegister() throws EmailAlreadyExistsException{
        var dto = new RegisterRequestDTO("tester", "tester@test.com", "123@Test");
        var registeredUser = new User("useruuid", "tester", "tester@test.com", "123@Test", new ArrayList<Role>());
        when(registerUserUseCase.execute(dto)).thenReturn(registeredUser);
        
        var response = controller.register(dto);
        assertEquals(registeredUser, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("Should not register user when using email already registered")
    public void testFailedRegister() throws EmailAlreadyExistsException{
        var dto = new RegisterRequestDTO("tester", "tester@test.com", "123@Test");
        when(registerUserUseCase.execute(dto)).thenThrow(EmailAlreadyExistsException.class);
        
        assertThrows(EmailAlreadyExistsException.class, () -> controller.register(dto));
    }
}
