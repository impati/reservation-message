package com.example.reservationmessagebatch.job.send_reservation_message;

import com.example.reservationmessagedomain.domain.reservation.reservation_message.ReservationMessage;
import com.example.reservationmessagedomain.domain.reservation.reservation_message.ReservationMessageRepository;
import com.example.reservationmessagedomain.domain.sent_message.SentMessageRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.batch.job.names=send_reservation_message")
@SpringBatchTest
class SendReservationConfigurationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @SpyBean
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

    @Test
    @DisplayName("ReservationMessage 에서 한 명의 사용자에게 두 개의 메시지를 보낼 수 없다.")
    void skipRun() throws Exception {
        String path = "src/test/resources/sameMemberNumber.csv";
        LocalDateTime registeredAt = LocalDateTime.of(2024, 9, 1, 0, 0, 0);
        String content = "hello world";
        ReservationMessage reservationMessage = reservationMessageRepository.save(new ReservationMessage(content, path, registeredAt));
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("inputFile", path)
                .addLong("reservationMessageId", reservationMessage.getId())
                .toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        assertThat(sentMessageRepository.findAll()).hasSize(11)
                .extracting("content")
                .contains(content);
        ReservationMessage foundReservationMessage = reservationMessageRepository.findById(reservationMessage.getId()).orElseThrow();
        assertThat(foundReservationMessage.isDone()).isTrue();
    }

    @Test
    @DisplayName("배치 실행 중 중간에 실패해도 다시 실행했을 때 결과는 같아야한다.")
    void reRun() throws Exception {
        String path = "src/test/resources/sameMemberNumber.csv";
        LocalDateTime registeredAt = LocalDateTime.of(2024, 9, 1, 0, 0, 0);
        String content = "hello world1";
        ReservationMessage reservationMessage = reservationMessageRepository.save(new ReservationMessage(content, path, registeredAt));
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("inputFile", path)
                .addLong("reservationMessageId", reservationMessage.getId())
                .toJobParameters();

        // first when & then
        doThrow(IllegalArgumentException.class)
                .when(sentMessageRepository)
                .findSentMessageByReservationMessageAndMemberNumber(any(ReservationMessage.class), eq("99999"));
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.FAILED);
        assertThat(sentMessageRepository.findAll()).hasSize(9);

        // second when & then
        when(sentMessageRepository.findSentMessageByReservationMessageAndMemberNumber(any(ReservationMessage.class), eq("99999")))
                .thenReturn(Optional.empty());
        JobExecution jobExecution2 = jobLauncherTestUtils.launchJob(jobParameters);
        assertThat(jobExecution2.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        assertThat(sentMessageRepository.findAll()).hasSize(11);
    }
}
