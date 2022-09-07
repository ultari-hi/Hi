package com.hi.service;

import com.hi.dto.ReservationDto;
import com.hi.repository.AccommodationRepository;
import com.hi.repository.ReservationRepository;
import com.hi.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;


    public List<ReservationDto> reservationList(Long userId){
        return new ArrayList<>(reservationRepository.findAllByUserId(userId)
                .stream()
                .map(ReservationDto::new)
                .collect(toUnmodifiableList()));
    }
}
