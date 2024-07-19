package bookify.ms.book.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import bookify.ms.book.core.gateways.BookGateway;
import bookify.ms.book.core.usecases.RegisterBookUseCase;
import bookify.ms.book.core.utils.BookStorageService;
import bookify.ms.book.data.aws.BookStorageServiceImpl;
import bookify.ms.book.data.gatewaysImpl.BookGatewayImpl;
import bookify.ms.book.data.mappers.BookMapper;
import bookify.ms.book.data.repositories.BookRepository;

@Configuration
public class BookConfig {
    @Bean
    public BookMapper bookMapper() {
        return new BookMapper();
    }

    @Bean
    public BookGateway bookGateway(BookRepository bookRepository, BookMapper bookMapper) {
        return new BookGatewayImpl(bookRepository, bookMapper);
    }

    @Bean
    public BookStorageService bookStorageService() {
        return new BookStorageServiceImpl();
    }

    @Bean
    public RegisterBookUseCase registerBookUseCase(BookGateway bookGateway, BookStorageService bookStorageService) {
        return new RegisterBookUseCase(bookGateway, bookStorageService);
    }
}
