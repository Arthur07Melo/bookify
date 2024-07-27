package bookify.ms.book.infra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import bookify.ms.book.core.gateways.BookGateway;
import bookify.ms.book.core.usecases.RegisterBookUseCase;
import bookify.ms.book.core.utils.BookStorageService;
import bookify.ms.book.data.gatewaysImpl.BookGatewayImpl;
import bookify.ms.book.data.mappers.BookMapper;
import bookify.ms.book.data.repositories.BookRepository;
import bookify.ms.book.infra.services.BookStorageServiceImpl;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class BookConfig {
    @Value("${cloud.aws.s3.bucketname}")
    private String bucketName;

    @Bean
    BookMapper bookMapper() {
        return new BookMapper();
    }

    @Bean
    BookGateway bookGateway(BookRepository bookRepository, BookMapper bookMapper) {
        return new BookGatewayImpl(bookRepository, bookMapper);
    }

    @Bean
    BookStorageService bookStorageService(S3Client s3client) {
        return new BookStorageServiceImpl(s3client, bucketName);
    }

    @Bean
    RegisterBookUseCase registerBookUseCase(BookGateway bookGateway, BookStorageService bookStorageService) {
        return new RegisterBookUseCase(bookGateway, bookStorageService);
    }
}
