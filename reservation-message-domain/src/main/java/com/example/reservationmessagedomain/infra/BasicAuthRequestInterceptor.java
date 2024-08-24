package com.example.reservationmessagedomain.infra;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Base64;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BasicAuthRequestInterceptor implements RequestInterceptor {

    private final String username;
    private final String password;

    @Override
    public void apply(final RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes()));
    }
}
