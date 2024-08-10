package com.example.reservationmessage.domain.reservation.reservation_disabled_time;

import java.time.LocalTime;
import java.util.List;

public class ReservationDisabledTimes {

    private final List<ReservationDisabledTime> disabledTimes;

    private ReservationDisabledTimes(List<ReservationDisabledTime> reservationDisabledTimes) {
        this.disabledTimes = reservationDisabledTimes;
    }

    public static ReservationDisabledTimes from(List<ReservationDisabledTime> reservationDisabledTimes) {
        return new ReservationDisabledTimes(reservationDisabledTimes);
    }

    public boolean contain(LocalTime reservationTime) {
        return disabledTimes.stream().anyMatch(it -> it.isEqual(reservationTime));
    }
}
