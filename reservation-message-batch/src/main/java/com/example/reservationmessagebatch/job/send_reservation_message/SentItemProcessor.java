package com.example.reservationmessagebatch.job.send_reservation_message;

import com.example.reservationmessagedomain.domain.reservation.reservation_message.ReservationMessage;
import com.example.reservationmessagedomain.domain.sent_message.SentMessage;
import com.example.reservationmessagedomain.domain.sent_message.SentMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class SentItemProcessor implements ItemProcessor<String, SentMessage> {

    private final SentMessageRepository sentMessageRepository;
    private final SendReservationMessageContextHolder contextHolder;

    public SentItemProcessor(SentMessageRepository sentMessageRepository, SendReservationMessageContextHolder contextHolder) {
        this.sentMessageRepository = sentMessageRepository;
        this.contextHolder = contextHolder;
    }

    @Override
    public SentMessage process(final String memberNumber) {
        ReservationMessage reservationMessage = contextHolder.getReservationMessage();
        if (sentMessageRepository.findSentMessageByReservationMessageAndMemberNumber(reservationMessage, memberNumber).isPresent()) {
            return null;
        }

        return new SentMessage(memberNumber, contextHolder.getReservationMessage());
    }
}
