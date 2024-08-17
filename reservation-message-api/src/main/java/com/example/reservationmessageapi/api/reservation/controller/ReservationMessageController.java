package com.example.reservationmessageapi.api.reservation.controller;

import com.example.reservationmessageapi.api.reservation.application.ReservationMessageService;
import com.example.reservationmessageapi.api.reservation.request.ReservationMessageRequest;
import com.example.reservationmessageapi.api.reservation.response.ReservationMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationMessageController {

    private final ReservationMessageService reservationMessageService;

    @PostMapping("/reservation-messages")
    public ReservationMessageResponse create(@ModelAttribute ReservationMessageRequest request) {
        return reservationMessageService.create(request);
    }

    @GetMapping("/reservation-messages/{reservationMessageId}")
    public ReservationMessageResponse get(@PathVariable Long reservationMessageId) {
        return reservationMessageService.getReservationMessage(reservationMessageId);
    }
}
