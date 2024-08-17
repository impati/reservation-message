package com.example.reservationmessageapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {"com.example.reservationmessageapi", "com.example.reservationmessagedomain"}
)
public class ReservationMessageApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationMessageApiApplication.class, args);
    }

}
