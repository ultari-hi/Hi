package com.hi.service;

import com.hi.domain.*;
import com.hi.dto.PaymentReqDto;
import com.hi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;
    private final RoomRepository roomRepository;

    //결제,예약 생성
    public Long newPayment(PaymentReqDto dto){
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        Accommodation accommodation = accommodationRepository.findById(dto.getAccommodationId())
                .orElseThrow(() -> new IllegalArgumentException("숙소를 찾을 수 없습니다."));
        Room room = roomRepository.findById(dto.getRoomId());
        Reservation newReservation = Reservation.newReservation(user, accommodation, room, dto);
        Reservation reservation = reservationRepository.save(newReservation);
        Payment newPayment = Payment.newPayment(dto, user, reservation);
        paymentRepository.save(newPayment);
        return reservation.getId();
    }

    //결제,예약 결과에 따른 상태 수정
    public void paymentResult(PaymentReqDto dto){
        Reservation reservation = reservationRepository.findById(dto.getReservationId())
                .orElseThrow(() -> new IllegalArgumentException("예약 정보를 찾을 수 없습니다."));
        Payment payment = paymentRepository.findById(reservation.getId())
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));
        payment.status(dto.getStatus());
        reservation.status(dto.getStatus());
    }
}
