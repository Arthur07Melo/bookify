package bookify.ms.book.core.usecases;

import bookify.ms.book.core.domain.Role;
import bookify.ms.book.core.domain.User;
import bookify.ms.book.core.dtos.RegisterUserDTO;
import bookify.ms.book.core.exceptions.EmailAlreadyExistsException;
import bookify.ms.book.core.gateways.UsersGateway;
import bookify.ms.book.core.utils.Encoder;

public class RegisterUserUseCase {
    private UsersGateway userGateway;
    private Encoder encoder;

    public RegisterUserUseCase(UsersGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(RegisterUserDTO dto) throws EmailAlreadyExistsException {
        User userCheck = userGateway.findByEmail(dto.email());
        if (userCheck != null) throw new EmailAlreadyExistsException();

        String encodedPwd = encoder.encodePassword(dto.password()); 

        var user = new User(dto.name(), dto.email(), encodedPwd);

        Role userRole = new Role();
        userRole.setRoleName("ROLE_USER");
        user.getRoles().add(userRole);

        return userGateway.save(user);
    }
}
