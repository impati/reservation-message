package com.example.reservationmessageapi.schedule;

import com.example.reservationmessagedomain.domain.reservation.reservation_message.ReservationMessage;
import com.example.reservationmessagedomain.domain.reservation.reservation_message.ReservationMessageRepository;
import com.example.reservationmessagedomain.domain.sent_message.SentMessageRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReservationScheduledTaskTest {

    @Autowired
    private ReservationMessageRepository reservationMessageRepository;

    @Autowired
    private ReservationScheduledTask reservationScheduledTask;

    @Autowired
    private SentMessageRepository sentMessageRepository;

    @AfterEach
    void tearDown() {
        sentMessageRepository.deleteAll();
        reservationMessageRepository.deleteAll();
    }

    @Test
    @DisplayName("")
    void runReservation() throws InterruptedException {
        // given
        int inter = 2;
        CountDownLatch countDownLatch = new CountDownLatch(inter);
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        LocalDateTime now = LocalDateTime.of(2024, 8, 24, 12, 0);
        reservationMessageRepository.saveAll(List.of(
                new ReservationMessage(
                        "hello world1",
                        "member1.csv",
                        now
                ),
                new ReservationMessage(
                        "hello world2",
                        "member2.csv",
                        now
                ))
        );

        // when
        for (int i = 0; i < inter; i++) {
            executorService.submit(() -> {
                try {
                    reservationScheduledTask.runReservation(now);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        // then
        assertThat(sentMessageRepository.count()).isEqualTo(2);
    }
}
