package com.example.reservationmessageapi.schedule;

import com.example.reservationmessagedomain.domain.reservation.ReservationMessageExecutor;
import com.example.reservationmessagedomain.domain.reservation.reservation_message.ReservationMessage;
import com.example.reservationmessagedomain.domain.sent_message.SentMessage;
import com.example.reservationmessagedomain.domain.sent_message.SentMessageRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class FakeReservationMessageExecutor implements ReservationMessageExecutor {

    private final SentMessageRepository sentMessageRepository;

    public FakeReservationMessageExecutor(SentMessageRepository sentMessageRepository) {
        this.sentMessageRepository = sentMessageRepository;
    }

    @Override
    public void execute(final ReservationMessage reservationMessage) {
        sentMessageRepository.save(new SentMessage("0000", reservationMessage));
    }
}
