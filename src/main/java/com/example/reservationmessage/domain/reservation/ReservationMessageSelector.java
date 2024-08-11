package com.example.reservationmessage.domain.reservation;

import com.example.reservationmessage.domain.reservation.reservation_message.ReservationMessage;
import com.example.reservationmessage.domain.reservation.reservation_message.ReservationMessageRepository;
import com.example.reservationmessage.domain.reservation.reservation_message.ReservationTime;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationMessageSelector {

    private final ReservationMessageRepository repository;

    public List<ReservationMessage> findTarget(ReservationTime reservationTime) {

        return repository.findTargetReservationMessage(reservationTime.getValue()).stream()
                .filter(ReservationMessage::isBefore)
                .sorted(Comparator.comparing(ReservationMessage::getRegisteredAt))
                .toList();
    }
}
