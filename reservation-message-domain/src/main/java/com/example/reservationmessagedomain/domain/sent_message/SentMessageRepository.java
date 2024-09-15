package com.example.reservationmessagedomain.domain.sent_message;

import com.example.reservationmessagedomain.domain.reservation.reservation_message.ReservationMessage;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SentMessageRepository extends JpaRepository<SentMessage, Long> {

    Optional<SentMessage> findSentMessageByReservationMessageAndMemberNumber(
            ReservationMessage reservationMessage,
            String memberNumber
    );
}
