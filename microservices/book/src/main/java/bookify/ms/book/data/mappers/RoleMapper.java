package bookify.ms.book.data.mappers;

import bookify.ms.book.core.domain.Role;
import bookify.ms.book.data.entities.RoleEntity;

public class RoleMapper {
    public Role toDomain(RoleEntity entity){
        return new Role(
            entity.getId(),
            entity.getRoleName()
        );
    }

    public RoleEntity toEntity(Role domainObj) {
        return new RoleEntity(
            domainObj.getId(),
            domainObj.getRoleName()
        );
    }
}
