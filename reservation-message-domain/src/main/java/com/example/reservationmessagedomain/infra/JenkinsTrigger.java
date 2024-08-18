package com.example.reservationmessagedomain.infra;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "jenkins",
        url = "${jenkins.url}",
        configuration = JenkinsFeignConfiguration.class
)
public interface JenkinsTrigger {

    @PostMapping("/job/send-reservation-message/buildWithParameters")
    void trigger(@RequestParam String inputFile, @RequestParam Long reservationMessageId);
}
