package bookify.ms.book.core.gateways;

import bookify.ms.book.core.domain.Book;

public interface BookGateway {
    public Book save(Book book);
}
