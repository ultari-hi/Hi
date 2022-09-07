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

import static org.springframework.data.util.StreamUtils.toUnmodifiableList;

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
    private final TmpDateRepository tmpDateRepository;

    //결제,예약 생성
    public String createPayment(PaymentReqDto dto){
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("객실을 찾을 수 없습니다."));

        Accommodation accommodation = accommodationRepository.findById(room.getAccommodation().getId())
                .orElseThrow(() -> new IllegalArgumentException("숙소를 찾을 수 없습니다."));

        Payment payment = Payment.newPayment(dto, user);
        paymentRepository.save(payment);

        Reservation reservation = Reservation.newReservation(user, accommodation, room, dto);
        reservationRepository.save(reservation);

        List<LocalDate> selectDates = dto.getCheckInDate()
                    .datesUntil(dto.getCheckOutDate().plusDays(1))
                    .collect(toUnmodifiableList());

        List<TmpDate> tmpDates = selectDates
                .stream()
                .map(date -> new TmpDate(payment, date))
                .collect(toUnmodifiableList());

        tmpDateRepository.saveAll(tmpDates);

        return "success";
    }

    //결제,예약 결과에 따른 상태 수정
    public Status paymentResult(PaymentReqDto dto){
        Payment payment = paymentRepository.findById(dto.getPaymentId())
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));

        //결제된 금액 검증
        Status result = payment.verify(dto.getAmountPaid());

        //포인트 사용/적립
        if(result.equals(Status.SUCCESS)) {
            Point latestPoint = pointRepository.findBalance(payment.getUser());
            List<Point> point = Point.newPoint(payment, latestPoint);
            pointRepository.saveAll(point);

            List<LocalDate> dates = tmpDateRepository.findAllById(payment);
            List<ReservationDate> reservationDates = dates
                    .stream()
                    .map(date -> new ReservationDate(payment.getReservation(), payment.getReservation().getRoom(), date))
                    .collect(toUnmodifiableList());
            reservationDateRepository.saveAll(reservationDates);
        }
        return result;
    }
}
