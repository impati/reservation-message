package com.example.reservationmessagebatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {"com.example.reservationmessagebatch", "com.example.reservationmessagedomain"}
)
public class ReservationMessageBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationMessageBatchApplication.class, args);
    }
}
