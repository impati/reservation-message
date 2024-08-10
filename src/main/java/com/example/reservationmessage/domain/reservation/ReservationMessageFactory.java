package com.example.reservationmessage.domain.reservation;

import com.example.reservationmessage.domain.reservation.reservation_disabled_time.ReservationDisabledTimeRepository;
import com.example.reservationmessage.domain.reservation.reservation_disabled_time.ReservationDisabledTimes;
import com.example.reservationmessage.domain.reservation.reservation_message.ReservationMessage;
import com.example.reservationmessage.domain.reservation.reservation_message.ReservationMessageRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationMessageFactory {

    private final ReservationMessageRepository reservationMessageRepository;
    private final ReservationDisabledTimeRepository reservationDisabledTimeRepository;

    public ReservationMessage create(String content, String memberNumberFilePath, LocalDateTime reservationTime) {
        ReservationDisabledTimes reservationDisabledTimes = ReservationDisabledTimes.from(reservationDisabledTimeRepository.findAll());
        if (reservationDisabledTimes.contain(reservationTime.toLocalTime())) {
            throw new IllegalArgumentException("예약 가능한 시간이 아닙니다.");
        }

        return reservationMessageRepository.save(new ReservationMessage(content, memberNumberFilePath, reservationTime));
    }
}
