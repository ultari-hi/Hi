package com.hi.service;

import com.hi.domain.*;
import com.hi.dto.accommodation.ReviewReqDto;
import com.hi.dto.accommodation.ReviewResDto;
import com.hi.repository.AccommodationRepository;
import com.hi.repository.ReservationRepository;
import com.hi.repository.ReviewRepository;
import com.hi.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final AccommodationRepository accommodationRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    //리뷰 쓰기
    public String createReview(User user, ReviewReqDto dto){
        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(()-> new IllegalArgumentException("객실을 찾을 수 없습니다."));

        Reservation reservation = reservationRepository.findById(dto.getReservationId())
                .orElseThrow(() -> new IllegalArgumentException("예약 내역을 찾을 수 없습니다."));

        Review review = Review.newReview(user, room, reservation, dto);

        reviewRepository.save(review);

        return "success";
    }

    //숙소에 대한 리뷰 조회
    public List<ReviewResDto> findReview(Long accommodationId) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(()-> new IllegalArgumentException("숙소를 찾을 수 없습니다."));

        List<Room> rooms = accommodation.getRooms();
        List<Review> reviews = reviewRepository.findByRooms(rooms).orElseThrow(()-> new IllegalArgumentException("리뷰가 없습니다."));

        return reviews.stream()
                .map(ReviewResDto::new)
                .collect(Collectors.toUnmodifiableList());
    }
}
