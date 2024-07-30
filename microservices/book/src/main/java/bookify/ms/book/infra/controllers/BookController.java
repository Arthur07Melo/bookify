package bookify.ms.book.infra.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import bookify.ms.book.core.domain.Book;
import bookify.ms.book.core.dtos.request.RegisterBookDTO;
import bookify.ms.book.core.usecases.RegisterBookUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/book")
@Slf4j
public class BookController {
    private final RegisterBookUseCase registerBookUseCase;

    public BookController(RegisterBookUseCase registerBookUseCase) {
        this.registerBookUseCase = registerBookUseCase;
    }

    @PostMapping
    // @RequestBody RegisterBookDTO dto
    public ResponseEntity<Book> postBook(
        @RequestParam("file") MultipartFile file,
        @RequestParam("title") String title,
        @RequestParam("price") float price,
        @RequestParam("gender") String gender,
        @RequestParam("author") String author
        ) {
        log.info("Starting new book register");
        var dto = new RegisterBookDTO(title, price, gender, author);
        File bookFile = convertMultipartFileToFile(file);
        var book = registerBookUseCase.execute(dto, bookFile);
        return ResponseEntity.status(201).body(book);
    }
    

    
    private File convertMultipartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try {
            Files.copy(file.getInputStream(), convertedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return convertedFile;
    }

}
