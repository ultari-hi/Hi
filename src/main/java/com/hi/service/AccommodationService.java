package com.hi.service;

import com.hi.domain.Accommodation;
import com.hi.domain.AccommodationImage;
import com.hi.domain.RoomImage;
import com.hi.domain.User;
import com.hi.dto.ImageDto;
import com.hi.dto.accommodation.AccommodationDetailDto;
import com.hi.dto.accommodation.AccommodationReqDto;
import com.hi.dto.accommodation.AccommodationResDto;
import com.hi.dto.accommodation.RoomResDto;
import com.hi.repository.AccommodationImageRepository;
import com.hi.repository.AccommodationRepository;
import com.hi.repository.ReservationDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.StreamUtils;
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
    private final ReservationDateRepository reservationDateRepository;

    //숙소, 사진 등록
    public String registerAccommodation(User user, AccommodationReqDto dto) {
        Accommodation accommodation = accommodationRepository.save(Accommodation.newAccommodation(dto, user));

        if (dto.getImageUrls() != null){
            List<AccommodationImage> images = dto.getImageUrls()
                    .stream()
                    .map(url -> AccommodationImage.newAccommodationImage(accommodation, url))
                    .collect(Collectors.toUnmodifiableList());
            accommodationImageRepository.saveAll(images);
        }
        return "success";
    }

    //숙소 상세 조회
    public AccommodationDetailDto findAccommodationDetail(Long accommodationId, LocalDate checkInDate, LocalDate checkOutDate, Integer numberPeople) {
        final List<Long> unAvailableRoomIds = new ArrayList<>();
        if (checkInDate != null) {
            List<LocalDate> selectDates = checkInDate
                    .datesUntil(checkOutDate.plusDays(1))
                    .collect(StreamUtils.toUnmodifiableList());
            unAvailableRoomIds.addAll(reservationDateRepository.unAvailableRooms(selectDates));
        }

        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new IllegalArgumentException("숙소를 찾을 수 없습니다."));

        ImageDto urls = new ImageDto(accommodation.getAccommodationImages()
                .stream()
                .map(AccommodationImage::getUrl)
                .collect(toUnmodifiableList()));

        //숙소에 포함된 조건에 안맞는 객실들
        List<RoomResDto> unAvailableRoomResDto = accommodation.getRooms().stream()
                .filter(room -> unAvailableRoomIds.contains(room.getId()) || room.getNumberPeople() < numberPeople)
                .map(room -> new RoomResDto(room, false, new ImageDto(room.getRoomImages().stream()
                        .map(RoomImage::getUrl)
                        .collect(toUnmodifiableList()))))
                .collect(toUnmodifiableList());

        //숙소에 포함된 조건에 맞는 객실들
        List<RoomResDto> availableRoomResDto = accommodation.getRooms().stream()
                .filter(room -> !unAvailableRoomIds.contains(room.getId()) && room.getNumberPeople() >= numberPeople)
                .map(room -> new RoomResDto(room, true, new ImageDto(room.getRoomImages().stream()
                        .map(RoomImage::getUrl)
                        .collect(toUnmodifiableList()))))
                .collect(toUnmodifiableList());

        List<RoomResDto> roomResDto = new ArrayList<>();
        roomResDto.addAll(unAvailableRoomResDto);
        roomResDto.addAll(availableRoomResDto);

        return new AccommodationDetailDto(accommodation, urls, roomResDto);
    }

    //필터링 한 숙소 리스트
    public List<AccommodationResDto> findAccommodationsFiltering(LocalDate checkInDate, LocalDate checkOutDate, int numberPeople, String region, List<String> accommodationFiltering, List<String> roomFiltering) {
        //체크인 날짜부터 체크아웃 날짜까지 리스트로 만들기
        List<LocalDate> selectDates = checkInDate
                .datesUntil(checkOutDate.plusDays(1))
                .collect(StreamUtils.toUnmodifiableList());

        List<Long> unAvailableRoomIds = reservationDateRepository.unAvailableRooms(selectDates);

        List<Accommodation> accommodations = accommodationRepository.findAvailableAccommodations(unAvailableRoomIds, numberPeople, region, accommodationFiltering, roomFiltering);

        return accommodations.stream()
                .map(accommodation -> new AccommodationResDto(accommodation, new ImageDto(accommodation.getAccommodationImages()
                        .stream()
                        .map(AccommodationImage::getUrl)
                        .collect(toUnmodifiableList()))))
                .collect(Collectors.toUnmodifiableList());
    }

    //숙소 전체 리스트
    public List<AccommodationResDto> findAllAccommodation() {
        List<Accommodation> accommodations = accommodationRepository.findAll();
        return accommodations.stream()
                .filter(Accommodation::hasRoom)
                .map(accommodation -> new AccommodationResDto(accommodation, new ImageDto(accommodation.getAccommodationImages()
                        .stream()
                        .map(AccommodationImage::getUrl)
                        .collect(toUnmodifiableList()))))
                .collect(Collectors.toUnmodifiableList());
    }

    //숙소 수정
    public void modifyAccommodation(Long accommodationId, AccommodationReqDto dto) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(()->new IllegalArgumentException("숙소를 찾을 수 없습니다."));
        accommodation.update(dto);
    }

}
