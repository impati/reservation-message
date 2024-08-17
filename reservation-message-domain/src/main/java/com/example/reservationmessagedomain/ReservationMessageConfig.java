package com.example.reservationmessagedomain;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = {ReservationMessageBase.class})
@EntityScan(basePackageClasses = {ReservationMessageBase.class})
@ComponentScan(basePackageClasses = {ReservationMessageBase.class})
public class ReservationMessageConfig {

}
