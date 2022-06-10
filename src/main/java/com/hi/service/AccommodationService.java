package com.hi.service;

import com.hi.domain.Accommodation;
import com.hi.dto.AccommodationReqDto;
import com.hi.dto.AccommodationResDto;
import com.hi.error.AccommodationNotFoundException;
import com.hi.repository.AccommodationRepository;
import com.hi.repository.ReservationRepository;
import com.hi.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@Service
@Transactional
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    //숙소 등록
    public void saveAccommodation(AccommodationReqDto dto) {
        Accommodation accommodation = Accommodation.createAccommodation(dto);
        accommodationRepository.save(accommodation);
    }

    //숙소 상세 조회
    public AccommodationResDto findOne(Long accommodationId) {
        return new AccommodationResDto(accommodationRepository.findDetail(accommodationId)
                .orElseThrow(() -> new AccommodationNotFoundException("숙소를 찾을 수 없습니다.")));
    }

    //숙소 리스트
    public List<AccommodationResDto> accommodationList(LocalDate checkInDate, LocalDate checkOutDate, int number_of_people, String region) {
        List<Long> notAvailableRoom = reservationRepository.notAvailableRoom(checkInDate, checkOutDate);
        List<Long> findAvailableRoom = roomRepository.findAvailableRoom(notAvailableRoom, number_of_people);
        return new ArrayList<>(accommodationRepository.findAccommodationList(findAvailableRoom, region))
                .stream()
                .map(AccommodationResDto::new)
                .collect(toUnmodifiableList());
    }

    //숙소 수정
    public void modifyAccommodation(Long accommodationId, AccommodationReqDto dto) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new AccommodationNotFoundException("숙소를 찾을 수 없습니다."));
        accommodation.modifyAccommodation(dto);
        accommodationRepository.save(accommodation);
    }

}
