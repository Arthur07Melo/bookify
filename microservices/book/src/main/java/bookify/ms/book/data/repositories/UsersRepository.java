package bookify.ms.book.data.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bookify.ms.book.data.entities.UserEntity;


public interface UsersRepository extends JpaRepository<UserEntity, String> {
    public Optional<UserEntity> findByEmail(String email);
}
