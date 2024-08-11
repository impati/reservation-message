package com.example.reservationmessage.domain.reservation.reservation_message;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationTime {

    @Column(name = "reservation_time")
    LocalDateTime value;

    private ReservationTime(final LocalDateTime value) {
        this.value = value;
    }

    public static ReservationTime from(final LocalDateTime reservationTime) {
        LocalTime localTime = reservationTime.toLocalTime();
        if (!(localTime.getMinute() == 0 || localTime.getMinute() == 30)) {
            throw new IllegalArgumentException("예약 가능한 시간은 00분 또는 30분 이어야합니다.");
        }

        return new ReservationTime(reservationTime);
    }
}
