package com.example.reservationmessagebatch.support;

import com.example.reservationmessagedomain.domain.FileRepository;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.PathResource;
import org.springframework.stereotype.Component;

@Primary
@Component
public class TestFileRepository implements FileRepository {

    @Override
    public String uploadFile(final String originFileName, final InputStream inputStream, final Long contentLength) {
        return "";
    }

    @Override
    public byte[] downLoadFile(final String path) {
        try {
            return new PathResource(path).getContentAsByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
