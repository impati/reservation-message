package com.example.reservationmessage.api.reservation.response;

import com.example.reservationmessage.domain.reservation.reservation_message.ReservationMessage;
import java.time.LocalDateTime;

public record ReservationMessageResponse(

        Long reservationId,
        String content,
        LocalDateTime reservationAt,
        LocalDateTime registeredAt
) {

    public static ReservationMessageResponse from(ReservationMessage reservationMessage) {
        return new ReservationMessageResponse(
                reservationMessage.getId(),
                reservationMessage.getContent(),
                reservationMessage.getReservationAt(),
                reservationMessage.getRegisteredAt()
        );
    }
}
