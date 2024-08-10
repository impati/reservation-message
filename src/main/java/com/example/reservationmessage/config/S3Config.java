package com.example.reservationmessage.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@Getter
public class S3Config {

    private final String region;
    private final String bucketName;
    private final String directory;
    private final String accessKey;
    private final String secretKey;

    public S3Config(
            @Value("${aws.s3.region}")
            String region,
            @Value("${aws.s3.bucket-name}")
            String bucketName,
            @Value("${aws.s3.directory}")
            String directory,
            @Value("${aws.s3.access-key}")
            String accessKey,
            @Value("${aws.s3.secret-key}")
            String secretKey
    ) {
        this.region = region;
        this.bucketName = bucketName;
        this.directory = directory;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    @Bean
    public S3Client configurationS3Client() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of(region))
                .build();
    }
}
