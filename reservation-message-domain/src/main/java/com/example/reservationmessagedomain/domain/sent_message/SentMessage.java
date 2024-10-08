package com.example.reservationmessagedomain.domain.sent_message;

import com.example.reservationmessagedomain.domain.reservation.reservation_message.ReservationMessage;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SentMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sent_message_id")
    Long id;

    @ManyToOne
    @JoinColumn(name = "reservation_mssage_id")
    ReservationMessage reservationMessage;

    @Column(name = "member_number")
    String memberNumber;

    @Column(name = "content")
    String content;

    @Column(name = "is_checked")
    boolean isChecked;

    @Column(name = "sent_at")
    LocalDateTime sentAt;

    public SentMessage(final String memberNumber, final ReservationMessage reservationMessage) {
        this.memberNumber = memberNumber;
        this.content = reservationMessage.getContent();
        this.isChecked = false;
        this.reservationMessage = reservationMessage;
        this.sentAt = LocalDateTime.now();
    }

    public void check() {
        this.isChecked = true;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SentMessage that)) {
            return false;
        }
        if (Objects.isNull(this.getId())) {
            return memberNumber.equals(that.memberNumber) && reservationMessage.equals(that.reservationMessage);
        }
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
