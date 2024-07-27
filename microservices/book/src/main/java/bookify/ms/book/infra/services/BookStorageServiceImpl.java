package bookify.ms.book.infra.services;

import java.io.File;

import javax.management.RuntimeErrorException;

import bookify.ms.book.core.utils.BookStorageService;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

public class BookStorageServiceImpl implements BookStorageService {
    private final S3Client s3client;
    private final String bucketName;

    public BookStorageServiceImpl(S3Client s3client, String bucketName) {
        this.s3client = s3client;
        this.bucketName = bucketName;
    }

    @Override
    public String saveBook(File bookFile) {
        try{
            PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(bookFile.getName())
                .build();
            
            s3client.putObject(request, RequestBody.fromFile(bookFile.toPath()));

            return "s3://" + bucketName + "/" + bookFile.getName();
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeErrorException(null, "error while trying to insert file inside bucket");
        }
    }
    
}
