package com.example.reservationmessage.domain.reservation;

import com.example.reservationmessage.domain.reservation.reservation_message.ReservationMessage;
import com.example.reservationmessage.domain.reservation.reservation_message.ReservationTime;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationScheduledTask {

    private static final int HOLD_TIME = 100;

    private final ReservationTimeResolver reservationTimeResolver;
    private final ReservationMessageSelector reservationMessageSelector;
    private final ReservationMessageExecutor reservationMessageExecutor;

    @Scheduled(cron = "0 0,30 * * * *")
    public void performTask() throws InterruptedException {
        ReservationTime reservationTime = reservationTimeResolver.convertReservationTime(LocalDateTime.now());
        List<ReservationMessage> reservationMessages = reservationMessageSelector.findTarget(reservationTime);

        for (ReservationMessage reservationMessage : reservationMessages) {
            Thread.sleep(HOLD_TIME);
            reservationMessageExecutor.execute(reservationMessage);
        }
    }
}
