package bookify.ms.book.data.mappers;

import bookify.ms.book.core.domain.User;
import bookify.ms.book.data.entities.UserEntity;

public class UserMapper {
    private final RoleMapper roleMapper;

    public UserMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public User toDomain(UserEntity entity){
        return new User(
            entity.getId(),
            entity.getName(),
            entity.getEmail(),
            entity.getPassword(),
            entity.getRoles().stream().map(roleMapper::toDomain).toList()
        );
    }

    public UserEntity toEntity(User domainObj){
        return new UserEntity(
            domainObj.getId(),
            domainObj.getName(),
            domainObj.getEmail(),
            domainObj.getPassword(),
            domainObj.getRoles().stream().map(roleMapper::toEntity).toList()
        );
    }
}
