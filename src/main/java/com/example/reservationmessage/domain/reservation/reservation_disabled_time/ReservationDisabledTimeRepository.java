package com.example.reservationmessage.domain.reservation.reservation_disabled_time;

import java.time.LocalTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationDisabledTimeRepository extends JpaRepository<ReservationDisabledTime, LocalTime> {

}
