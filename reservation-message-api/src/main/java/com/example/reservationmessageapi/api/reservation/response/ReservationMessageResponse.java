package com.example.reservationmessageapi.api.reservation.response;

import com.example.reservationmessagedomain.domain.reservation.reservation_message.ReservationMessage;
import java.time.LocalDateTime;

public record ReservationMessageResponse(

        Long reservationId,
        String content,
        String memberNumberFilePath,
        LocalDateTime reservationAt,
        LocalDateTime registeredAt
) {

    public static ReservationMessageResponse from(ReservationMessage reservationMessage) {
        return new ReservationMessageResponse(
                reservationMessage.getId(),
                reservationMessage.getContent(),
                reservationMessage.getMemberNumberFilePath(),
                reservationMessage.getReservationAt(),
                reservationMessage.getRegisteredAt()
        );
    }
}
