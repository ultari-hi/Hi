package com.hi.service;

import com.hi.domain.*;
import com.hi.dto.PaymentReqDto;
import com.hi.dto.ReservationDto;
import com.hi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;
    private final RoomRepository roomRepository;

    public void newPayment(PaymentReqDto paymentReqDto, ReservationDto reservationDto, Long userId, Long accommodationId, Long roomId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new IllegalArgumentException("숙소를 찾을 수 없습니다."));
        Room room = roomRepository.findById(roomId);
        Payment newPayment = Payment.newPayment(paymentReqDto, user);
        Payment payment = paymentRepository.save(newPayment);
        Reservation reservation = Reservation.newReservation(user, accommodation, room, payment, reservationDto);
        reservationRepository.save(reservation);
    }
}
