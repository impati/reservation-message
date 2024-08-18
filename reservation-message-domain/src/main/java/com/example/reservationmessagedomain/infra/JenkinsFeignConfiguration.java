package com.example.reservationmessagedomain.infra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class JenkinsFeignConfiguration {

    private final String username;
    private final String password;

    public JenkinsFeignConfiguration(
            @Value("${jenkins.user}") String username,
            @Value("${jenkins.api_token}") String password) {
        this.username = username;
        this.password = password;
    }

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(username, password);
    }
}
