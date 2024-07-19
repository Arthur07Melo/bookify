package bookify.ms.book.infra.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import bookify.ms.book.core.domain.Book;
import bookify.ms.book.core.dtos.request.RegisterBookDTO;
import bookify.ms.book.core.usecases.RegisterBookUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/book")
@Slf4j
public class BookController {
    private final RegisterBookUseCase registerBookUseCase;

    public BookController(RegisterBookUseCase registerBookUseCase) {
        this.registerBookUseCase = registerBookUseCase;
    }

    @PostMapping
    public ResponseEntity<Book> postMethodName(@RequestBody RegisterBookDTO dto) {
        log.info("Starting new book register");
        var book = registerBookUseCase.execute(dto, null);
        return ResponseEntity.status(201).body(book);
    }
    
}
