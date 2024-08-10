package com.example.reservationmessage.domain;

import java.io.InputStream;

public interface FileRepository {

    String uploadFile(String originFileName, InputStream inputStream, Long contentLength);

    byte[] downLoadFile(String path);
}
