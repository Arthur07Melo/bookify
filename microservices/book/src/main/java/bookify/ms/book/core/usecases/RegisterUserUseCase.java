package bookify.ms.book.core.usecases;

import bookify.ms.book.core.domain.Role;
import bookify.ms.book.core.domain.User;
import bookify.ms.book.core.dtos.UserDTO;
import bookify.ms.book.core.exceptions.EmailAlreadyExistsException;
import bookify.ms.book.core.gateways.UserGateway;

public class RegisterUserUseCase {
    private UserGateway userGateway;

    public RegisterUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(UserDTO dto) throws EmailAlreadyExistsException {
        User userCheck = userGateway.findByEmail(dto.email());
        if (userCheck != null) throw new EmailAlreadyExistsException();
        var user = new User(dto.name(), dto.email(), dto.password());

        Role userRole = new Role();
        userRole.setRoleName("ROLE_USER");
        user.getRoles().add(userRole);

        return userGateway.save(user);
    }
}
