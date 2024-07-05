package bookify.ms.book.data.gatewaysImpl;

import bookify.ms.book.core.domain.User;
import bookify.ms.book.core.gateways.UsersGateway;
import bookify.ms.book.data.mappers.UserMapper;
import bookify.ms.book.data.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;

public class UsersGatewayImpl implements UsersGateway{
    private UsersRepository usersRepository;
    private final UserMapper userMapper;

    public UsersGatewayImpl(UsersRepository usersRepository, UserMapper userMapper) {
        this.userMapper = userMapper;
        this.usersRepository = usersRepository;
    }

    @Override
    public User save(User user) {
        var userEntity = userMapper.toEntity(user);
        return userMapper.toDomain(usersRepository.save(userEntity));
    }

    @Override
    public User findByEmail(String email) {
        var userOptional = usersRepository.findByEmail(email);
        if (userOptional.isEmpty()) return null;
        return userMapper.toDomain(userOptional.get());
    }
    
}
