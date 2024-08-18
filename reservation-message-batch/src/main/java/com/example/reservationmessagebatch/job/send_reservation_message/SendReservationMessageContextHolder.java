package com.example.reservationmessagebatch.job.send_reservation_message;

import com.example.reservationmessagedomain.domain.reservation.reservation_message.ReservationMessage;
import lombok.Getter;

@Getter
public class SendReservationMessageContextHolder {

    private final ReservationMessage reservationMessage;

    public SendReservationMessageContextHolder(final ReservationMessage reservationMessage) {
        this.reservationMessage = reservationMessage;
    }
}
