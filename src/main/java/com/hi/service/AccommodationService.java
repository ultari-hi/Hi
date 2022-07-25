package com.hi.service;

import com.hi.domain.Accommodation;
import com.hi.domain.AccommodationImage;
import com.hi.dto.AccommodationReqDto;
import com.hi.dto.AccommodationResDto;
import com.hi.dto.ImageDto;
import com.hi.repository.AccommodationImageRepository;
import com.hi.repository.AccommodationRepository;
import com.hi.repository.ReservationRepository;
import com.hi.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toUnmodifiableList;

@Service
@Transactional
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final AccommodationImageRepository accommodationImageRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    //숙소, 사진 등록
    public void saveAccommodation(AccommodationReqDto dto) {
        Accommodation accommodation = Accommodation.createAccommodation(dto);
        Accommodation accommodationId = accommodationRepository.save(accommodation);
        List<AccommodationImage> images = dto.getUrlList()
                .stream()
                .map(url -> AccommodationImage.create(accommodationId, url))
                .collect(Collectors.toUnmodifiableList());
        accommodationImageRepository.saveAll(images);
    }

    //숙소 상세 조회
    public AccommodationResDto findOne(Long accommodationId) {
        return new AccommodationResDto(accommodationRepository.findById(accommodationId));
    }

    //숙소 리스트
    public List<AccommodationResDto> accommodationList(LocalDate checkInDate, LocalDate checkOutDate, int number_of_people, String region) {
        List<Long> findAvailableRoomIds = reservationRepository.notAvailableRoom(checkInDate, checkOutDate);
        List<Long> findAvailableAccommodationIds = roomRepository.findAvailableRoom(findAvailableRoomIds, number_of_people);
        return new ArrayList<>(accommodationRepository.findAccommodationList(findAvailableAccommodationIds, region)
                .stream()
                .map(AccommodationResDto::new)
                .collect(toUnmodifiableList()));
    }

    //숙소 수정
    public void modifyAccommodation(Long accommodationId, AccommodationReqDto dto) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId);
        accommodation.update(dto);
    }

    //숙소 사진 조회
    public ImageDto accommodationImages(Long accommodationId) {
        List<String> urlList = accommodationImageRepository.findAll(accommodationId)
                .stream()
                .map(AccommodationImage::getUrl)
                .collect(toUnmodifiableList());
        return new ImageDto(urlList);
    }

}
