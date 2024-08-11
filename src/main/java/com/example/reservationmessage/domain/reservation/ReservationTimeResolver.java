package com.example.reservationmessage.domain.reservation;

import com.example.reservationmessage.domain.reservation.reservation_message.ReservationTime;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * 앞뒤로 5분간 간격을 둔다.
 */
@Component
public class ReservationTimeResolver {

    private static final List<Integer> ZERO_TIME_GAPS = List.of(
            55, 56, 57, 58, 59, 0, 1, 2, 3, 4, 5
    );
    private static final List<Integer> THIRTY_TIME_GAPS = List.of(
            25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35
    );

    public ReservationTime convertReservationTime(LocalDateTime now) {
        if (ZERO_TIME_GAPS.contains(now.getMinute())) {
            return ReservationTime.from(LocalDateTime.of(now.toLocalDate(), LocalTime.of(now.getHour(), 0)));
        }
        if (THIRTY_TIME_GAPS.contains(now.getMinute())) {
            return ReservationTime.from(LocalDateTime.of(now.toLocalDate(), LocalTime.of(now.getHour(), 30)));
        }

        throw new IllegalStateException("호출 시간이 잘못되었습니다. = " + now);
    }
}
