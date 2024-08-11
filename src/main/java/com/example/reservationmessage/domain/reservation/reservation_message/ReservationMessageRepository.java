package com.example.reservationmessage.domain.reservation.reservation_message;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationMessageRepository extends JpaRepository<ReservationMessage, Long> {

    @Query("SELECT rm FROM ReservationMessage rm WHERE rm.reservationAt.value = :now")
    List<ReservationMessage> findTargetReservationMessage(@Param("now") LocalDateTime now);
}
