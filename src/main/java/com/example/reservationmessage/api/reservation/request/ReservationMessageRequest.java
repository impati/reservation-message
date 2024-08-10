package com.example.reservationmessage.api.reservation.request;

import java.time.LocalDateTime;
import org.springframework.web.multipart.MultipartFile;

public record ReservationMessageRequest(

        String content,
        MultipartFile file,
        LocalDateTime reservationAt
) {

}
