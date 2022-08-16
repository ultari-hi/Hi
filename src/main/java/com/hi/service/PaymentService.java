package com.hi.service;

import com.hi.domain.*;
import com.hi.dto.PaymentReqDto;
import com.hi.enums.Status;
import com.hi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;
    private final RoomRepository roomRepository;
    private final PointRepository pointRepository;
    private final ReservationDateRepository reservationDateRepository;

    //결제,예약 생성
    public Long newPayment(PaymentReqDto dto){
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("객실을 찾을 수 없습니다."));
        Accommodation accommodation = accommodationRepository.findById(room.getAccommodation().getId())
                .orElseThrow(() -> new IllegalArgumentException("숙소를 찾을 수 없습니다."));
        Reservation newReservation = Reservation.newReservation(user, accommodation, room, dto);
        Reservation reservation = reservationRepository.save(newReservation);
        Payment newPayment = Payment.newPayment(dto, user, reservation);
        paymentRepository.save(newPayment);
        return reservation.getId();
    }

    //결제,예약 결과에 따른 상태 수정
    public String paymentResult(PaymentReqDto dto){
        Reservation reservation = reservationRepository.findById(dto.getReservationId())
                .orElseThrow(() -> new IllegalArgumentException("예약 정보를 찾을 수 없습니다."));
        Payment payment = paymentRepository.findById(reservation.getId())
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));
        if (dto.getStatus().equals(Status.SUCCESS)){
            Room room = roomRepository.findById(dto.getRoomId())
                    .orElseThrow(() -> new IllegalArgumentException("객실을 찾을 수 없습니다."));
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(()-> new IllegalArgumentException("유저를 찾을 수 없습니다."));
            Point point = new Point(user, dto.getCashPay(), 0);
            pointRepository.save(point);
            payment.status(Status.SUCCESS);
            reservation.status(Status.SUCCESS);
            List<LocalDate> selectDates = dto.getCheckInDate()
                    .datesUntil(dto.getCheckOutDate().plusDays(1))
                    .collect(Collectors.toList());
            List<ReservationDate> dates = selectDates
                    .stream()
                    .map(date -> ReservationDate.create(reservation, room, date))
                    .collect(Collectors.toUnmodifiableList());
            reservationDateRepository.saveAll(dates);
            return "success";
        } else {
            payment.status(Status.FAIL);
            reservation.status(Status.FAIL);
            return "fail";
        }
    }
}
