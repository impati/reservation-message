package com.example.reservationmessagedomain.domain.reservation.reservation_message;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;
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
    @Enumerated(EnumType.STRING)
    ReservationStatus status;

    public ReservationMessage(final String content, final String memberNumberFilePath, final LocalDateTime reservationAt) {
        this.content = content;
        this.memberNumberFilePath = memberNumberFilePath;
        this.reservationAt = ReservationTime.from(reservationAt);
        this.registeredAt = LocalDateTime.now();
        this.status = ReservationStatus.BEFORE;
    }

    public boolean isBefore() {
        return this.status == ReservationStatus.BEFORE;
    }

    public boolean isDone() {
        return this.status == ReservationStatus.DONE;
    }

    public void active() {
        this.status = ReservationStatus.ING;
    }

    public LocalDateTime getReservationAt() {
        return reservationAt.value;
    }

    public void done() {
        this.status = ReservationStatus.DONE;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReservationMessage that)) {
            return false;
        }
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "ReservationMessage{" +
                "id=" + getId() +
                ", reservationAt=" + reservationAt +
                ", registeredAt=" + registeredAt +
                ", content='" + content + '\'' +
                ", memberNumberFilePath='" + memberNumberFilePath + '\'' +
                ", status=" + status +
                '}';
    }
}
