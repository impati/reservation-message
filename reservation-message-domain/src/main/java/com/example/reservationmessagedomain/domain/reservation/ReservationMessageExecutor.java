package com.example.reservationmessagedomain.domain.reservation;

import com.example.reservationmessagedomain.domain.reservation.reservation_message.ReservationMessage;

public interface ReservationMessageExecutor {

    void execute(ReservationMessage reservationMessage);
}
