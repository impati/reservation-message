package com.example.reservationmessagedomain.domain.reservation.reservation_message;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationMessageRepository extends JpaRepository<ReservationMessage, Long> {

    @Query(value = "SELECT * FROM reservation_message rm WHERE reservation_time = :now FOR UPDATE SKIP LOCKED ", nativeQuery = true)
    List<ReservationMessage> findTargetReservationMessage(@Param("now") LocalDateTime now);
}
