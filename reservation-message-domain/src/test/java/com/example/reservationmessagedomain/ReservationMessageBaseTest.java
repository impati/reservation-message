package com.example.reservationmessagedomain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@EnableAutoConfiguration
@SpringBootTest(classes = ReservationMessageConfig.class)
class ReservationMessageBaseTest {

    @Test
    void contextTest() {
    }
}
