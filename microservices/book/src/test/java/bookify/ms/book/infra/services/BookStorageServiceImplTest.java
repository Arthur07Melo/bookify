package bookify.ms.book.infra.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@ExtendWith(MockitoExtension.class)
public class BookStorageServiceImplTest {
    @Mock
    private S3Client s3Client;

    @InjectMocks
    private BookStorageServiceImpl bookStorageService;

    @BeforeEach
    public void setupTest() throws IOException {
        File newTestFile = new File("testFile.txt");
        newTestFile.createNewFile();
    }

    @AfterEach
    public void afterTest() {
        File newTestFile = new File("testFile.txt");
        newTestFile.delete();
    }

    @Test
    @DisplayName("Should save book file with success")
    public void testSuccessSaveBook() {
        bookStorageService = new BookStorageServiceImpl(s3Client, "books");
        File inputBookFile = new File("testFile.txt");
        lenient().when(s3Client.putObject(any(PutObjectRequest.class), eq(RequestBody.fromFile(inputBookFile.toPath()))))
            .thenReturn(PutObjectResponse.builder().build());
        String resultFilePath = bookStorageService.saveBook(inputBookFile);
        String expectedFilePath = "s3://books/" + inputBookFile.getName();
        assertEquals(expectedFilePath, resultFilePath);
    }
}
