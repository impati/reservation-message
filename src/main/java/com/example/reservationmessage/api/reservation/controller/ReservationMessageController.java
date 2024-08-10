package com.example.reservationmessage.api.reservation.controller;

import com.example.reservationmessage.api.reservation.application.ReservationMessageService;
import com.example.reservationmessage.api.reservation.request.ReservationMessageRequest;
import com.example.reservationmessage.api.reservation.response.ReservationMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationMessageController {

    private final ReservationMessageService reservationMessageService;

    @PostMapping("/reservation-message")
    public ReservationMessageResponse create(@RequestBody ReservationMessageRequest request) {
        return reservationMessageService.create(request);
    }
}
