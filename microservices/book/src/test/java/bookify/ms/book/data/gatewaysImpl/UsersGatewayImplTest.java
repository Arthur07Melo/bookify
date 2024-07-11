package bookify.ms.book.data.gatewaysImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import bookify.ms.book.core.domain.Role;
import bookify.ms.book.core.domain.User;
import bookify.ms.book.core.gateways.UsersGateway;
import bookify.ms.book.data.entities.RoleEntity;
import bookify.ms.book.data.entities.UserEntity;
import bookify.ms.book.data.mappers.RoleMapper;
import bookify.ms.book.data.mappers.UserMapper;
import bookify.ms.book.data.repositories.UsersRepository;
import jakarta.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
public class UsersGatewayImplTest {
    final EntityManager entityManager;

    final UsersRepository usersRepository;

    UsersGateway usersGateway;
    
    @Autowired
    public UsersGatewayImplTest(EntityManager entityManager, UsersRepository usersRepository) {
        this.entityManager = entityManager;
        this.usersRepository = usersRepository;
        usersGateway = new UsersGatewayImpl(usersRepository, new UserMapper(new RoleMapper()));
    }


    @Test
    @DisplayName("findByEmail method should get user sucessfully from database")
    public void testFindByEmailSuccess(){
        String email = "test@test.com";
        createUser(
            "tester", email, "123@Test", new ArrayList<RoleEntity>()
        );

        var expectedUser = new User("myuseruuid", "tester", email, "123@Test", new ArrayList<Role>());
        var myUser = usersGateway.findByEmail(email);
        expectedUser.setId(myUser.getId()); //do not compare ids
        assertEquals(expectedUser, myUser);
    }

    @Test
    @DisplayName("findByEmail method should return null when data not found in database")
    public void testFindByEmailRegisterNotFound(){
        var user = usersGateway.findByEmail("inexistentemail@test.com");
        User expectedUser = null;

        assertEquals(expectedUser, user);
    }
    


    private void createUser(String name, String email, String password, List<RoleEntity> roles){
        var userEntity = new UserEntity();
        userEntity.setName(name);
        userEntity.setEmail(email);
        userEntity.setPassword(password);
        userEntity.setRoles(roles);
        entityManager.persist(userEntity);
    }
}
