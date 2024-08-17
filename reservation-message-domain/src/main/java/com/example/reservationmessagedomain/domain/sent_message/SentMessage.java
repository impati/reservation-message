package com.example.reservationmessagedomain.domain.sent_message;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SentMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sent_message_id")
    Long id;

    @Column(name = "member_number")
    String memberNumber;

    @Column(name = "content")
    String content;

    @Column(name = "is_checked")
    boolean isChecked;

    public SentMessage(final String memberNumber, final String content) {
        this.memberNumber = memberNumber;
        this.content = content;
        this.isChecked = false;
    }

    public void check() {
        this.isChecked = true;
    }
}
