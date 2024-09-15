package com.example.reservationmessagebatch.job.send_reservation_message;

import com.example.reservationmessagedomain.domain.sent_message.SentMessage;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.database.JpaItemWriter;

public class SentMessageWriter extends JpaItemWriter<SentMessage> {

    @Override
    public void write(final Chunk<? extends SentMessage> items) {
        super.write(new Chunk<>(items.getItems().stream().distinct().toList()));
    }
}
