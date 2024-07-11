package bookify.ms.book.core.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import bookify.ms.book.core.domain.Role;
import bookify.ms.book.core.domain.User;
import bookify.ms.book.core.dtos.request.RegisterRequestDTO;
import bookify.ms.book.core.exceptions.EmailAlreadyExistsException;
import bookify.ms.book.core.gateways.UsersGateway;
import bookify.ms.book.core.utils.Encoder;

@ExtendWith(MockitoExtension.class)
public class RegisterUserUseCaseTest {

    @Mock
    UsersGateway usersGateway;
    @Mock
    Encoder encoder;

    @InjectMocks
    RegisterUserUseCase useCase;

    @Test
    @DisplayName("Should register successfully when passing valid data")
    public void testRegisterSuccess() throws EmailAlreadyExistsException {
        var dto = new RegisterRequestDTO("teste", "teste@teste.com", "123@abcd");
        when(usersGateway.findByEmail(dto.email()))
        .thenReturn(null);

        when(encoder.encodePassword(dto.password()))
        .thenReturn("encodedPwd");
        
        var roles = new ArrayList<Role>();
        roles.add(new Role(2L, "ROLE_USER"));

        var registeredUser = new User(dto.name(), dto.email(), "encodedPwd", roles);

        when(usersGateway.save(Mockito.any(User.class)))
        .thenReturn(registeredUser);

        assertEquals(registeredUser, useCase.execute(dto));
    }

    @Test
    @DisplayName("Should not register when passing email that is already registered")
    public void testRegisterEmailAlreadyExist() throws EmailAlreadyExistsException {
        var dto = new RegisterRequestDTO("teste", "teste@teste.com", "123@abcd");
        when(usersGateway.findByEmail(dto.email()))
        .thenReturn(new User());

        assertThrows(EmailAlreadyExistsException.class, () -> useCase.execute(dto));
    }
}
