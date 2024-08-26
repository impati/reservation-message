package com.example.reservationmessagebatch.job.send_reservation_message;

import com.example.reservationmessagedomain.domain.FileRepository;
import com.example.reservationmessagedomain.domain.reservation.reservation_message.ReservationMessage;
import com.example.reservationmessagedomain.domain.reservation.reservation_message.ReservationMessageRepository;
import com.example.reservationmessagedomain.domain.sent_message.SentMessage;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ConditionalOnProperty(value = "spring.batch.job.names", havingValue = "send_reservation_message")
@RequiredArgsConstructor
public class SendReservationConfiguration {

    private final FileRepository fileRepository;
    private final ReservationMessageRepository reservationMessageRepository;

    @Bean
    public Job sendReservationMessageJob(
            JobRepository jobRepository,
            PlatformTransactionManager platformTransactionManager,
            EntityManagerFactory entityManagerFactory
    ) {
        return new JobBuilder("send_reservation_message_job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(sendReservationMessageStep(jobRepository, platformTransactionManager, entityManagerFactory))
                .listener(reservationMessageJobFinishListener())
                .build();
    }

    @Bean
    @JobScope
    public SendReservationMessageContextHolder sendReservationMessageContextHolder(
            @Value("#{jobParameters['reservationMessageId']}") Long reservationId
    ) {
        ReservationMessage reservationMessage = reservationMessageRepository.findById(reservationId)
                .orElseThrow(IllegalArgumentException::new);
        return new SendReservationMessageContextHolder(reservationMessage);
    }

    @Bean
    @JobScope
    public ReservationMessageJobFinishListener reservationMessageJobFinishListener() {
        return new ReservationMessageJobFinishListener(
                sendReservationMessageContextHolder(null),
                reservationMessageRepository
        );
    }

    @Bean
    @JobScope
    public Step sendReservationMessageStep(
            JobRepository jobRepository,
            PlatformTransactionManager platformTransactionManager,
            EntityManagerFactory entityManagerFactory
    ) {
        return new StepBuilder("send_reservation_message_step", jobRepository)
                .<String, SentMessage>chunk(10, platformTransactionManager)
                .reader(reservationMessageReader(null))
                .processor(reservationMessageProcessor(null))
                .writer(reservationMessageWriter(entityManagerFactory))
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<String> reservationMessageReader(@Value("#{jobParameters['inputFile']}") String inputFile) {
        return new FlatFileItemReaderBuilder<String>()
                .name("memberNumber")
                .resource(new ByteArrayResource(fileRepository.downLoadFile(inputFile)))
                .strict(false)
                .lineMapper(new PassThroughLineMapper())
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<String, SentMessage> reservationMessageProcessor(SendReservationMessageContextHolder contextHolder) {
        return memberNumber -> new SentMessage(memberNumber, contextHolder.getReservationMessage());
    }

    @Bean
    @StepScope
    public ItemWriter<SentMessage> reservationMessageWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<SentMessage> sentMessageJpaItemWriter = new JpaItemWriter<>();
        sentMessageJpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return sentMessageJpaItemWriter;
    }
}
