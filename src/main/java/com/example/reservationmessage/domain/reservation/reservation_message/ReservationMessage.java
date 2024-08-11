package com.example.reservationmessage.domain.reservation.reservation_message;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReservationMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_message_id")
    Long id;

    @Column(name = "reservation_at")
    @Embedded
    ReservationTime reservationAt;

    @Column(name = "registered_at")
    LocalDateTime registeredAt;

    @Column(name = "content")
    String content;

    @Column(name = "member_number")
    String memberNumberFilePath;

    @Column(name = "status")
    ReservationStatus status;

    public ReservationMessage(final String content, final String memberNumberFilePath, final LocalDateTime reservationAt) {
        this.content = content;
        this.memberNumberFilePath = memberNumberFilePath;
        this.reservationAt = ReservationTime.from(reservationAt);
        this.registeredAt = LocalDateTime.now();
        this.status = ReservationStatus.BEFORE;
    }

    public boolean isActive() {
        return this.status == ReservationStatus.BEFORE;
    }

    public LocalDateTime getReservationAt() {
        return reservationAt.value;
    }

    @Override
    public String toString() {
        return "ReservationMessage{" +
                "id=" + id +
                ", reservationAt=" + reservationAt +
                ", registeredAt=" + registeredAt +
                ", content='" + content + '\'' +
                ", memberNumberFilePath='" + memberNumberFilePath + '\'' +
                ", status=" + status +
                '}';
    }
}
