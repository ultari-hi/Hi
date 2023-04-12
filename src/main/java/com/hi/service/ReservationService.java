package com.hi.service;

import com.hi.domain.*;
import com.hi.dto.ReservationDto;
import com.hi.dto.ReservationReqDto;
import com.hi.dto.ReservationResDto;
import com.hi.repository.PointRepository;
import com.hi.repository.ReservationDateRepository;
import com.hi.repository.ReservationRepository;
import com.hi.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.StreamUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    //객실 예약하기
    public String reserveRoom(User user, ReservationReqDto dto) {
        Room room = roomRepository.findById(dto.getRoomId()).orElseThrow(() -> new IllegalArgumentException("객실을 찾을 수 없습니다."));

        List<LocalDate> selectDates = dto.getCheckInDate()
                .datesUntil(dto.getCheckOutDate().plusDays(1))
                .collect(StreamUtils.toUnmodifiableList());

        List<LocalDate> unAvailableDates = reservationDateRepository.unAvailableDates(room, selectDates);

        if (unAvailableDates.isEmpty()) {
            String content = "객실 예약";
            Point afterPoint = Point.usePoint(user, dto.getTotalAmount(), content, pointRepository.findLatest(user));
            Point point = pointRepository.save(afterPoint);

            Reservation reservation = reservationRepository.save(Reservation.newReservation(user, room, point, dto));

            List<ReservationDate> reservationDates = selectDates.stream()
                    .map(dates -> new ReservationDate(reservation, room, dates))
                    .collect(Collectors.toUnmodifiableList());

            reservationDateRepository.saveAll(reservationDates);
            return "예약 성공";
        };
        return "이미 예약된 객실입니다. \n이용 불가 날짜 : " + unAvailableDates;
    }

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
        reservation.cancel(reservation);
        reservationDateRepository.cancelReservation(reservation);

        Point beforePoint = pointRepository.findLatest(user);
        Point afterPoint = Point.savePoint(user, reservation.getTotalAmount(), content, beforePoint);
        pointRepository.save(afterPoint);

        return "예약 취소 완료";
    }
}
