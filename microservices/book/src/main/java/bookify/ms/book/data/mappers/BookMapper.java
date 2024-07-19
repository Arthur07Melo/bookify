package bookify.ms.book.data.mappers;

import bookify.ms.book.core.domain.Book;
import bookify.ms.book.data.entities.BookEntity;

public class BookMapper {
    public Book toDomain(BookEntity entity) {
        return new Book(
            entity.getId(),
            entity.getTitle(),
            entity.getPrice(),
            entity.getGender(),
            entity.getAuthor(),
            entity.getContentPath()
        );
    }

    public BookEntity toEntity(Book domainObj) {
        return new BookEntity(
            domainObj.getId(),
            domainObj.getTitle(),
            domainObj.getPrice(),
            domainObj.getGender(),
            domainObj.getAuthor(),
            domainObj.getContentPath()
        );
    }
}
