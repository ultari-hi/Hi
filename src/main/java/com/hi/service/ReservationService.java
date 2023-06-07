package com.hi.service;

import com.hi.domain.*;
import com.hi.dto.ReservationDto;
import com.hi.dto.ReservationResDto;
import com.hi.enums.Status;
import com.hi.repository.PointRepository;
import com.hi.repository.ReservationDateRepository;
import com.hi.repository.ReservationRepository;
import com.hi.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationDateRepository reservationDateRepository;
    private final PointRepository pointRepository;
    private final RoomRepository roomRepository;

    //예약 상세보기
    public ReservationResDto reservationPage(User user, Long roomId) {
        Room room = roomRepository.findWithAccommodationById(roomId).orElseThrow(()-> new IllegalArgumentException("객실을 찾을 수 없습니다."));
        return new ReservationResDto(user, room);
    }

    //예약 내역
    public List<ReservationDto> findHistory(Long userId) {
        List<Reservation> reservations = reservationRepository.findHistoryByUserId(userId);
        return reservations.stream()
                .map(reservation -> new ReservationDto(reservation, reservation.getReservationDates().stream()
                        .map(ReservationDate::getDate)
                        .collect(Collectors.toList())))
                .collect(Collectors.toUnmodifiableList());
    }

    //예약 취소
    public String cancelReservation(User user, Long reservationId) {
        String content = "예약 취소";

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("예약 내역을 찾을 수 없습니다."));
        Payment payment = reservation.getPayment();
        Status status = payment.cancel();
        reservation.status(status);
        reservationDateRepository.cancelReservation(reservation);

        Point beforePoint = pointRepository.findLatest(user);
        Point afterPoint = Point.savePoint(user, reservation.getTotalAmount(), beforePoint, payment, content);
        pointRepository.save(afterPoint);

        return "예약 취소 완료";
    }
}
