package com.hi.service;

import com.hi.domain.Accommodation;
import com.hi.domain.AccommodationImage;
import com.hi.domain.Room;
import com.hi.dto.AccommodationReqDto;
import com.hi.dto.AccommodationResDto;
import com.hi.dto.ImageDto;
import com.hi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.StreamUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toUnmodifiableList;

@Service
@Transactional
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final AccommodationImageRepository accommodationImageRepository;
    private final ReservationDateRepository reservationDateRepository;

    //숙소, 사진 등록
    public void saveAccommodation(AccommodationReqDto dto) {
        Accommodation accommodation = Accommodation.createAccommodation(dto);
        accommodationRepository.save(accommodation);

        if (dto.getUrlList() != null){
        List<AccommodationImage> images = dto.getUrlList()
                .stream()
                .map(url -> AccommodationImage.create(accommodation, url))
                .collect(Collectors.toUnmodifiableList());
        accommodationImageRepository.saveAll(images);}
    }

    //숙소 상세 조회
    public AccommodationResDto findOne(Long accommodationId) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new IllegalArgumentException("숙소를 찾을 수 없습니다."));
        ImageDto urlList = new ImageDto(accommodation.getAccommodationImages()
                .stream()
                .map(AccommodationImage::getAcmImageUrl)
                .collect(Collectors.toUnmodifiableList()));
        return new AccommodationResDto(accommodation, urlList);
    }

    //필터링 한 숙소 리스트
    public List<AccommodationResDto> findAccommodations(LocalDate checkInDate, LocalDate checkOutDate, int numberOfPeople, String region) {
        List<LocalDate> selectDates = checkInDate
                .datesUntil(checkOutDate.plusDays(1))
                .collect(StreamUtils.toUnmodifiableList());
        List<Long> unAvailableRoomIds = reservationDateRepository.unAvailableRooms(selectDates);
        List<Accommodation> accommodations = accommodationRepository.findAvailableAccommodations(unAvailableRoomIds, numberOfPeople, region);
        return accommodations
                .stream()
                .map(accommodation -> new AccommodationResDto(accommodation, new ImageDto(accommodation.getAccommodationImages()
                        .stream()
                        .map(AccommodationImage::getAcmImageUrl)
                        .collect(toUnmodifiableList()))))
                .collect(Collectors.toList());
    }

    //숙소 전체 리스트
    public List<AccommodationResDto> findAllAccommodation() {
        List<Accommodation> accommodations = accommodationRepository.findAll();
        return accommodations
                .stream()
                .map(accommodation -> new AccommodationResDto(accommodation, new ImageDto(accommodation.getAccommodationImages()
                        .stream()
                        .map(AccommodationImage::getAcmImageUrl)
                        .collect(toUnmodifiableList()))))
                .collect(Collectors.toList());
    }

    //숙소 수정
    public void modifyAccommodation(Long accommodationId, AccommodationReqDto dto) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(()->new IllegalArgumentException("숙소를 찾을 수 없습니다."));
        accommodation.update(dto);
    }

}
