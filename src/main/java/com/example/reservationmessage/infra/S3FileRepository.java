package com.example.reservationmessage.infra;

import com.example.reservationmessage.config.S3Config;
import com.example.reservationmessage.domain.FileRepository;
import java.io.InputStream;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
public class S3FileRepository implements FileRepository {

    private final S3Client s3Client;
    private final S3Config s3Config;

    public S3FileRepository(S3Client s3Client, S3Config s3Config) {
        this.s3Client = s3Client;
        this.s3Config = s3Config;
    }

    @Override
    public String uploadFile(final String originFileName, final InputStream inputStream, final Long contentLength) {
        String savepoint = String.join("/", s3Config.getDirectory(), originFileName);
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(s3Config.getBucketName())
                .key(savepoint)
                .build();

        s3Client.putObject(request, RequestBody.fromInputStream(inputStream, contentLength));
        return String.format("https://%s.s3.%s.amazonaws.com/%s",
                s3Config.getBucketName(),
                s3Config.getRegion(),
                savepoint);
    }

    @Override
    public byte[] downLoadFile(final String path) {
        String savepoint = String.join("/", s3Config.getDirectory(), path);
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(s3Config.getBucketName())
                .key(savepoint)
                .build();
        return s3Client.getObjectAsBytes(request).asByteArray();
    }
}
