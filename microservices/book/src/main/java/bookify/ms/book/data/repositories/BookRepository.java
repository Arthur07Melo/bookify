package bookify.ms.book.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import bookify.ms.book.data.entities.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, String> {
    
}
