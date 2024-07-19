package bookify.ms.book.core.usecases;

import java.io.File;

import bookify.ms.book.core.domain.Book;
import bookify.ms.book.core.dtos.request.RegisterBookDTO;
import bookify.ms.book.core.gateways.BookGateway;
import bookify.ms.book.core.utils.BookStorageService;

public class RegisterBookUseCase {
    private final BookGateway bookGateway;
    //private final BookStorageService bookStorageService;

    public RegisterBookUseCase(BookGateway bookGateway, BookStorageService bookStorageService) {
        this.bookGateway = bookGateway;
        //this.bookStorageService = bookStorageService;
    }

    public Book execute(RegisterBookDTO dto, File bookFile) {
        //String filePath = bookStorageService.saveBook(bookFile);

        var bookToRegister = new Book(dto.title(), dto.price(), dto.gender(), dto.author(), "filePath");
        var registeredBook = bookGateway.save(bookToRegister);

        return registeredBook;
    }
}
