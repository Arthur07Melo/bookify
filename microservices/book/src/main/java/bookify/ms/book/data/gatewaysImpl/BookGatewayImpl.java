package bookify.ms.book.data.gatewaysImpl;

import bookify.ms.book.core.domain.Book;
import bookify.ms.book.core.gateways.BookGateway;
import bookify.ms.book.data.entities.BookEntity;
import bookify.ms.book.data.mappers.BookMapper;
import bookify.ms.book.data.repositories.BookRepository;

public class BookGatewayImpl implements BookGateway{
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookGatewayImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public Book save(Book book) {
        BookEntity bookEntity = bookMapper.toEntity(book);
        BookEntity registeredBookEntity = bookRepository.save(bookEntity);
        return bookMapper.toDomain(registeredBookEntity);
    }
    
}
