package com.example.reservationmessage.domain.reservation;

import com.example.reservationmessage.domain.reservation.reservation_message.ReservationMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationMessageExecutor {

    public void execute(ReservationMessage reservationMessage) {
        reservationMessage.active();
        log.info("execute reservationMessage = {} ", reservationMessage);
    }
}
