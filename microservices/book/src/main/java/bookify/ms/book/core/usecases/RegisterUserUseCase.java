package bookify.ms.book.core.usecases;

import java.util.ArrayList;

import bookify.ms.book.core.domain.Role;
import bookify.ms.book.core.domain.User;
import bookify.ms.book.core.dtos.request.RegisterRequestDTO;
import bookify.ms.book.core.exceptions.EmailAlreadyExistsException;
import bookify.ms.book.core.gateways.UsersGateway;
import bookify.ms.book.core.utils.Encoder;

public class RegisterUserUseCase {
    private UsersGateway userGateway;
    private Encoder encoder;

    public RegisterUserUseCase(UsersGateway userGateway, Encoder encoder) {
        this.userGateway = userGateway;
        this.encoder = encoder;
    }

    public User execute(RegisterRequestDTO dto) throws EmailAlreadyExistsException {
        User userCheck = userGateway.findByEmail(dto.email());
        if (userCheck != null) throw new EmailAlreadyExistsException();

        String encodedPwd = encoder.encodePassword(dto.password()); 
        var user = new User(dto.name(), dto.email(), encodedPwd, new ArrayList<Role>());

        addRoleToUser(user, "ROLE_USER");

        return userGateway.save(user);
    }

    private void addRoleToUser(User user, String role_name){
        var role = new Role();
        role.setRoleName(role_name);
        role.setId(2L);
        user.getRoles().add(role);
    }
}
