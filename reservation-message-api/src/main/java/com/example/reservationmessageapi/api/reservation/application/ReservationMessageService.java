package com.example.reservationmessageapi.api.reservation.application;

import com.example.reservationmessageapi.api.reservation.request.ReservationMessageRequest;
import com.example.reservationmessageapi.api.reservation.response.ReservationMessageResponse;
import com.example.reservationmessagedomain.domain.FileRepository;
import com.example.reservationmessagedomain.domain.reservation.ReservationMessageFactory;
import com.example.reservationmessagedomain.domain.reservation.reservation_message.ReservationMessageRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ReservationMessageService {

    private final ReservationMessageFactory reservationMessageFactory;
    private final ReservationMessageRepository reservationMessageRepository;
    private final FileRepository fileRepository;

    @Transactional
    public ReservationMessageResponse create(final ReservationMessageRequest request) {
        String uploadFilePath = uploadFile(request);

        return ReservationMessageResponse.from(
                reservationMessageFactory.create(request.content(), uploadFilePath, request.reservationAt())
        );
    }

    private String uploadFile(final ReservationMessageRequest request) {
        MultipartFile file = request.file();
        try {
            return fileRepository.uploadFile(file.getOriginalFilename(), file.getInputStream(), file.getSize());
        } catch (IOException e) {
            throw new IllegalStateException("파일 업로드에 실패했습니다.", e);
        }
    }

    @Transactional
    public ReservationMessageResponse getReservationMessage(final Long reservationMessageId) {
        return ReservationMessageResponse.from(
                reservationMessageRepository.findById(reservationMessageId).orElseThrow(IllegalArgumentException::new)
        );
    }
}
