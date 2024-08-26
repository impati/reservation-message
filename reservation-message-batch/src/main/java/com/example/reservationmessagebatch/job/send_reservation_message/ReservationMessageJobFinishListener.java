package com.example.reservationmessagebatch.job.send_reservation_message;

import com.example.reservationmessagedomain.domain.reservation.reservation_message.ReservationMessage;
import com.example.reservationmessagedomain.domain.reservation.reservation_message.ReservationMessageRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
public class ReservationMessageJobFinishListener implements JobExecutionListener {

    private final SendReservationMessageContextHolder contextHolder;
    private final ReservationMessageRepository repository;

    @Override
    public void afterJob(final @NonNull JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            ReservationMessage reservationMessage = repository.findById(contextHolder.getReservationMessage().getId()).orElseThrow();

            reservationMessage.done();
        }
    }
}
