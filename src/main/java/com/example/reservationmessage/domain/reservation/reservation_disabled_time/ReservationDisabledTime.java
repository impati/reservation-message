package com.example.reservationmessage.domain.reservation.reservation_disabled_time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class ReservationDisabledTime {

    @Id
    @Column(name = "time")
    LocalTime localTime;

    public ReservationDisabledTime(final LocalTime localTime) {
        this.localTime = localTime;
    }

    public boolean isEqual(LocalTime localTime) {
        return this.localTime.equals(localTime);
    }
}
