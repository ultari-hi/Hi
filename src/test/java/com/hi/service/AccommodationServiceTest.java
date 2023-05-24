package com.hi.service;

import com.hi.domain.Accommodation;
import com.hi.domain.User;
import com.hi.dto.accommodation.*;
import com.hi.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Slf4j
class AccommodationServiceTest {
    @Autowired AccommodationRepository accommodationRepository;
    @Autowired AccommodationImageRepository accommodationImageRepository;
    @Autowired ReservationDateRepository reservationDateRepository;
    @Autowired AccommodationService accommodationService;
    @Autowired RoomRepository roomRepository;
    @Autowired RoomImageRepository roomImageRepository;
    @Autowired RoomService roomService;
    @Autowired UserRepository userRepository;

    private final AccommodationReqDto testAcmDto = new AccommodationReqDto("테스트숙소", "TestAccommodation",
            "12345", "위치", "오시는 길", "소개",
            "서울", List.of("선풍기", "냉장고"), List.of("testImageUrl1", "testImageUrl2", "testImageUrl3"));

    private final RoomReqDto testRoomDto = new RoomReqDto("테스트 룸", "정보", "가이드", 1000,
            2, "도모토리", List.of("선풍기, 미니냉장고"), Boolean.TRUE, List.of("image1", "image123"));

    LocalDate checkInDate = LocalDate.of(2022, 1, 1);  // 체크인 날짜
    LocalDate checkOutDate = LocalDate.of(2022, 1, 4);  // 체크아웃 날짜
    int numberPeople = 2;  // 인원 수
    String region = "파리";  // 지역
    List<String> accommodationFiltering = List.of("냉장고", "테스트");  // 숙소 제공 필터링
    List<String> roomFiltering = List.of("드라이기", "미니냉장고");  // 객실 제공 필터링

    User user;

    @BeforeEach
    void init() {
        user = userRepository.findById(1L).orElseThrow(() -> new NoResultException("유저를 찾을 수 없습니다."));
    }

    @Test
    @DisplayName("숙소 등록")
    void registerAccommodation() {
        assertThat(accommodationService.registerAccommodation(user, testAcmDto)).isEqualTo("success");
    }

    @Test
    @DisplayName("숙소 등록, 사진 없어도 성공")
    void registerAccommodationNoImage() {
        testAcmDto.setImageUrls(null);
        assertThat(accommodationService.registerAccommodation(user, testAcmDto)).isEqualTo("success");
    }

    @Test
    @DisplayName("객실 등록 성공")
    void registerRoomSuccess() {
        assertThat(roomService.registerRoom(1L, testRoomDto)).isEqualTo("success");
    }

    @Test
    @DisplayName("객실 등록, 사진 없어도 성공")
    void registerRoomNoImage() {
        testRoomDto.setImageUrls(null);
        assertThat(roomService.registerRoom(1L, testRoomDto)).isEqualTo("success");
    }

    @Test
    @DisplayName("객실 등록 실패, 등록된 숙소 X")
    void registerRoomFail() {
        assertThatThrownBy(() -> roomService.registerRoom(0L, testRoomDto))
                .isInstanceOf(NoResultException.class)
                .hasMessageContaining("숙소를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("숙소 전체 조회")
    void findAllAccommodation() {
        assertThat(accommodationService.findAllAccommodation()).isNotEmpty();
        log.info("{}", accommodationService.findAllAccommodation());
    }

    @Test
    @DisplayName("숙소 상세 조회")
    void findAccommodationDetailDate() {
        AccommodationDetailDto accommodationDetailDto = accommodationService.findAccommodationDetail(1L, checkInDate, checkOutDate, 2);
        assertThat(accommodationDetailDto).isNotNull();

        RoomResDto room1 = accommodationDetailDto.getRooms().get(0);
        RoomResDto room2 = accommodationDetailDto.getRooms().get(1);

        // 입력 인원수가 객실 인원수보다 적으면 이용 불가
        assertThat(room1.getNumberPeople()).isLessThan(numberPeople);
        assertThat(room1.getIsAvailable()).isFalse();
        assertThat(room2.getNumberPeople()).isGreaterThan(numberPeople);
        assertThat(room2.getIsAvailable()).isTrue();
        log.info("{}", room1);
        log.info("{}", room2);
    }

    @Test
    @DisplayName("숙소 상세 조회, 체크 인아웃 날짜 = null")
    void findAccommodationDetail() {
        assertThat(accommodationService.findAccommodationDetail(1L, null, null, 1)).isNotNull();
    }

    @Test
    @DisplayName("숙소 리스트 필터링")
    void findAccommodationsFiltering() {
        List<String> noFiltering = List.of("없는 필터링");
        assertThat(accommodationService.findAccommodationsFiltering(
                checkInDate, checkOutDate, numberPeople, region, accommodationFiltering, roomFiltering)).isNotEmpty();
        assertThat(accommodationService.findAccommodationsFiltering(
                checkInDate, checkOutDate, numberPeople, region, noFiltering, roomFiltering)).isEmpty();  // 필터링에 없는 내용이라면 가져오지 않음
    }

    @Test
    @DisplayName("숙소 필터링 순서와 개수 달라도 같은 숙소")
    void reverseFiltering() {
        List<String> reverseAccommodationFiltering = ImmutableList.copyOf(accommodationFiltering).reverse();
        List<String> lessRoomFiltering = List.of("드라이기");
        List<AccommodationResDto> accommodation1 = accommodationService.findAccommodationsFiltering(
                checkInDate, checkOutDate, numberPeople, region, accommodationFiltering, roomFiltering);

        List<AccommodationResDto> accommodation2 = accommodationService.findAccommodationsFiltering(
                checkInDate, checkOutDate, numberPeople, region, reverseAccommodationFiltering, lessRoomFiltering);

        assertThat(accommodation1.get(0)).isEqualTo(accommodation2.get(0));
        log.info("accommodation1 : {}", accommodation1);
        log.info("accommodation2 : {}", accommodation2);
    }

    @Test
    @DisplayName("숙소 정보 수정")
    void modifyAccommodation() {
        testAcmDto.setNameKor("testNameKor");
        Accommodation accommodation = accommodationRepository.findById(1L).orElseThrow(() -> new NoResultException("숙소를 찾을 수 없습니다."));
        String nameKor = accommodation.getNameKor();
        accommodationService.modifyAccommodation(1L, testAcmDto);
        assertThat(accommodation.getNameKor()).isNotEqualTo(nameKor);
        assertThat(accommodation.getNameKor()).isEqualTo(testAcmDto.getNameKor());
        assertThat(accommodation.getNameKor()).isEqualTo("testNameKor");
    }
}