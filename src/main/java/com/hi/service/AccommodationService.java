package com.hi.service;

import com.hi.domain.Accommodation;
import com.hi.dto.AccommodationReqDto;
import com.hi.dto.AccommodationResDto;
import com.hi.repository.AccommodationRepositoryTest;
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
public class AccommodationService {

    private final AccommodationRepositoryTest accommodationRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    //숙소 등록
    public void saveAccommodation(AccommodationReqDto dto) {
        Accommodation accommodation = Accommodation.createAccommodation(dto);
        accommodationRepository.save(accommodation);
    }

    //숙소 상세 조회
    public AccommodationResDto findOne(Long accommodationId) {
        return new AccommodationResDto(accommodationRepository.findById(accommodationId));
    }

    //숙소 리스트
    public List<AccommodationResDto> accommodationList() {
        return new ArrayList<>(accommodationRepository.findAccommodationList()
                .stream()
                .map(AccommodationResDto::new)
                .collect(toUnmodifiableList()));
    }

    //숙소 수정
    public void modifyAccommodation(Long accommodationId, AccommodationReqDto dto) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId);
        accommodation.modifyAccommodation(dto);
        accommodationRepository.save(accommodation);
    }

}
