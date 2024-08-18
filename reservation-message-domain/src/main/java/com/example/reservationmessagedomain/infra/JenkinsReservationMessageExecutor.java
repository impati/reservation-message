package com.example.reservationmessagedomain.infra;

import com.example.reservationmessagedomain.domain.reservation.ReservationMessageExecutor;
import com.example.reservationmessagedomain.domain.reservation.reservation_message.ReservationMessage;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JenkinsReservationMessageExecutor implements ReservationMessageExecutor {

    private final JenkinsTrigger jenkinsTrigger;

    @Override
    public void execute(final ReservationMessage reservationMessage) {
        reservationMessage.active();
        log.info("time = {} , reservationMessage = {}", LocalDateTime.now(), reservationMessage);
        jenkinsTrigger.trigger(reservationMessage.getMemberNumberFilePath(), reservationMessage.getId());
    }
}
