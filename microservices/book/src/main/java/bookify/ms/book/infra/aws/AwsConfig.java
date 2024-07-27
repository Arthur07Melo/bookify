package bookify.ms.book.infra.aws;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;


@Configuration
public class AwsConfig {
    @Value("${cloud.aws.accesskey}")
    private String awsAccessKey;

    @Value("${cloud.aws.secretkey}")
    private String awsSecretKey;

    @Value("${cloud.aws.region}")
    private String awsRegion;

    @Value("${cloud.aws.s3.endpoint}")
    private String s3endpoint;

    @Bean
    S3Client amazonS3() {
        AwsCredentials awsCredentials = AwsBasicCredentials.create(awsAccessKey, awsSecretKey);

        return S3Client
            .builder()
            .region(Region.of(awsRegion))
            .endpointOverride(URI.create(s3endpoint))
            .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
            .forcePathStyle(true)
            .build();
    }

}
