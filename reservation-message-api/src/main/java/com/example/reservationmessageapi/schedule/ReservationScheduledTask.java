package com.example.reservationmessageapi.schedule;

import com.example.reservationmessagedomain.domain.reservation.ReservationMessageExecutor;
import com.example.reservationmessagedomain.domain.reservation.ReservationMessageSelector;
import com.example.reservationmessagedomain.domain.reservation.ReservationTimeResolver;
import com.example.reservationmessagedomain.domain.reservation.reservation_message.ReservationMessage;
import com.example.reservationmessagedomain.domain.reservation.reservation_message.ReservationTime;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationScheduledTask {

    private static final int HOLD_TIME = 100;

    private final ReservationTimeResolver reservationTimeResolver;
    private final ReservationMessageSelector reservationMessageSelector;
    private final ReservationMessageExecutor reservationMessageExecutor;

    @Scheduled(cron = "0 0,30 * * * *")
    @SchedulerLock(name = "reservation", lockAtLeastFor = "29m", lockAtMostFor = "29m")
    @Transactional
    public void runReservation() throws InterruptedException {
        runReservation(LocalDateTime.now());
    }

    void runReservation(LocalDateTime now) throws InterruptedException {
        ReservationTime reservationTime = reservationTimeResolver.convertReservationTime(now);
        log.info("LocalDateTime = {} , ReservationTime = {}", now, reservationTime);

        List<ReservationMessage> reservationMessages = reservationMessageSelector.findTarget(reservationTime);
        log.info("reservationMessages.size = {}", reservationMessages.size());
        for (ReservationMessage reservationMessage : reservationMessages) {
            Thread.sleep(HOLD_TIME);
            reservationMessageExecutor.execute(reservationMessage);
        }
    }
}
