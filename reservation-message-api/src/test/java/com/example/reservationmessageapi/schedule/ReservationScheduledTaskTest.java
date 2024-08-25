package com.example.reservationmessageapi.schedule;

import com.example.reservationmessagedomain.domain.reservation.reservation_message.ReservationMessage;
import com.example.reservationmessagedomain.domain.reservation.reservation_message.ReservationMessageRepository;
import com.example.reservationmessagedomain.domain.sent_message.SentMessageRepository;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.javacrumbs.shedlock.core.DefaultLockingTaskExecutor;
import net.javacrumbs.shedlock.core.LockConfiguration;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.core.LockingTaskExecutor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReservationScheduledTaskTest {

    @Autowired
    private ReservationMessageRepository reservationMessageRepository;

    @Autowired
    private ReservationScheduledTask reservationScheduledTask;

    @Autowired
    private SentMessageRepository sentMessageRepository;

    @Autowired
    private LockProvider lockProvider;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void tearDown() {
        sentMessageRepository.deleteAll();
        reservationMessageRepository.deleteAll();
    }

    @Test
    @DisplayName("skip lock 테스트")
    void runReservationSkipLocked() throws InterruptedException {
        // given
        int inter = 1; // 필요시에 값을 늘이자 ; ./gradlew clean build 이 실패함
        CountDownLatch countDownLatch = new CountDownLatch(inter);
        ExecutorService executorService = Executors.newFixedThreadPool(5); // 필요시에 값을 늘이자 ; ./gradlew clean build 이 실패함
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

    @Test
    @DisplayName("shed-lock 테스트")
    void reservationShedLock() throws Throwable {
        // given
        int inter = 1; // 필요시에 값을 늘이자 ; ./gradlew clean build 이 실패함
        CountDownLatch countDownLatch = new CountDownLatch(inter);
        ExecutorService executorService = Executors.newFixedThreadPool(5); // 필요시에 값을 늘이자 ; ./gradlew clean build 이 실패함
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
        String sql = """
                 CREATE TABLE shedlock(
                     name VARCHAR(64) NOT NULL,
                     lock_until TIMESTAMP(3) NOT NULL,
                     locked_at TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                     locked_by VARCHAR(255) NOT NULL, 
                     PRIMARY KEY (name)
                );
                 """;
        jdbcTemplate.execute(sql);
        LockingTaskExecutor executor = new DefaultLockingTaskExecutor(lockProvider);
        Instant lockAtMostUntil = Instant.now().plusSeconds(600);

        // when
        for (int i = 0; i < inter; i++) {
            executorService.submit(() -> {
                try {
                    executor.executeWithLock(() -> {
                        reservationScheduledTask.runReservation(now);
                        return null;
                    }, new LockConfiguration(
                            lockAtMostUntil,
                            "lockName",
                            Duration.ofSeconds(10),
                            Duration.ofSeconds(10)
                    ));
                } catch (Throwable e) {
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
