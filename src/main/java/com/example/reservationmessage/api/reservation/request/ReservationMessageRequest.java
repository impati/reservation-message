package com.example.reservationmessage.api.reservation.request;

import java.time.LocalDateTime;

public record ReservationMessageRequest(

        String content,
        String memberNumberFilePath,
        LocalDateTime reservationAt
) {

}
