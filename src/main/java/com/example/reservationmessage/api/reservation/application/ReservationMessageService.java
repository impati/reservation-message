package com.example.reservationmessage.api.reservation.application;

import com.example.reservationmessage.api.reservation.request.ReservationMessageRequest;
import com.example.reservationmessage.api.reservation.response.ReservationMessageResponse;
import com.example.reservationmessage.domain.reservation.ReservationMessageFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationMessageService {

    private final ReservationMessageFactory reservationMessageFactory;

    @Transactional
    public ReservationMessageResponse create(ReservationMessageRequest request) {
        return ReservationMessageResponse.from(
                reservationMessageFactory.create(request.content(), request.memberNumberFilePath(), request.reservationAt())
        );
    }
}
