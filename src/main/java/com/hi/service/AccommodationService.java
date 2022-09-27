package com.hi.service;

import com.hi.domain.*;
import com.hi.dto.*;
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
    private final UserRepository userRepository;

    //숙소, 사진 등록
    public void createAccommodation(AccommodationReqDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(()-> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        Accommodation accommodation = accommodationRepository.save(Accommodation.newAccommodation(dto, user));

        if (dto.getImageUrls() != null){
        List<AccommodationImage> images = dto.getImageUrls()
                .stream()
                .map(url -> AccommodationImage.newAccommodationImg(accommodation, url))
                .collect(Collectors.toUnmodifiableList());
        accommodationImageRepository.saveAll(images);}
    }

    //숙소 상세 조회
    public AccommodationDetailDto findAccommodationDetail(Long accommodationId) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new IllegalArgumentException("숙소를 찾을 수 없습니다."));

        ImageDto urls = new ImageDto(accommodation.getAccommodationImages()
                .stream()
                .map(AccommodationImage::getUrl)
                .collect(Collectors.toUnmodifiableList()));

        //숙소에 포함된 객실들
        List<RoomResDto> roomResDto = accommodation.getRooms().stream()
                .map(room -> new RoomResDto(room, new ImageDto(room.getRoomImages().stream()
                        .map(RoomImage::getUrl)
                        .collect(toUnmodifiableList()))))
                .collect(toUnmodifiableList());
        return new AccommodationDetailDto(accommodation, urls, roomResDto);
    }

    //필터링 한 숙소 리스트
    public List<AccommodationResDto> findAccommodations(LocalDate checkInDate, LocalDate checkOutDate, int numberPeople, String region) {
        //체크인 날짜부터 체크아웃 날짜까지 리스트로 만들기
        List<LocalDate> selectDates = checkInDate
                .datesUntil(checkOutDate.plusDays(1))
                .collect(StreamUtils.toUnmodifiableList());

        List<Long> unAvailableRoomIds = reservationDateRepository.unAvailableRooms(selectDates);

        List<Accommodation> accommodations = accommodationRepository.findAvailableAccommodations(unAvailableRoomIds, numberPeople, region);

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
        return accommodations
                .stream()
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
