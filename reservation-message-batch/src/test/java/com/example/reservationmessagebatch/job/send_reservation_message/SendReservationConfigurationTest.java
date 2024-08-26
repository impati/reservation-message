package com.example.reservationmessagebatch.job.send_reservation_message;

import com.example.reservationmessagedomain.domain.reservation.reservation_message.ReservationMessage;
import com.example.reservationmessagedomain.domain.reservation.reservation_message.ReservationMessageRepository;
import com.example.reservationmessagedomain.domain.sent_message.SentMessageRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.batch.job.names=send_reservation_message")
@SpringBatchTest
class SendReservationConfigurationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private SentMessageRepository sentMessageRepository;

    @Autowired
    private ReservationMessageRepository reservationMessageRepository;

    @AfterEach
    void tearDown() {
        sentMessageRepository.deleteAll();
        reservationMessageRepository.deleteAll();
    }

    @Test
    void sendReservationMessageJob() throws Exception {
        String path = "src/test/resources/memberNumber.csv";
        LocalDateTime registeredAt = LocalDateTime.of(2024, 9, 1, 0, 0, 0);
        String content = "hello world";
        ReservationMessage reservationMessage = reservationMessageRepository.save(new ReservationMessage(content, path, registeredAt));
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("inputFile", path)
                .addLong("reservationMessageId", reservationMessage.getId())
                .toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        assertThat(sentMessageRepository.findAll()).hasSize(2)
                .extracting("content")
                .contains(content);
        ReservationMessage foundReservationMessage = reservationMessageRepository.findById(reservationMessage.getId()).orElseThrow();
        assertThat(foundReservationMessage.isDone()).isTrue();
    }
}
