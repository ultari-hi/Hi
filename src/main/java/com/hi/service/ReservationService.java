package com.hi.service;

import com.hi.domain.Reservation;
import com.hi.domain.ReservationDate;
import com.hi.dto.ReservationDto;
import com.hi.repository.ReservationRepository;
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


    public List<ReservationDto> findHistory(Long userId){
        List<Reservation> reservations = reservationRepository.findHistoryByUserId(userId);
        return reservations.stream()
                .map(reservation -> new ReservationDto(reservation, reservation.getReservationDates().stream()
                        .map(ReservationDate::getDate)
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }
}
