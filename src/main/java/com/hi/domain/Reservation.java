package com.hi.domain;

import com.hi.dto.PaymentReqDto;
import com.hi.dto.ReservationDto;
import com.hi.enums.Status;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Reservation extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(columnDefinition = "text")
    private String enquiry;

    @Column(name = "check_in_date")
    private LocalDate checkInDate;

    @Column(name = "check_out_date")
    private LocalDate checkOutDate;

    @Column(columnDefinition = "char(10)")
    private String price;

    @Column(name = "price_kor")
    private int priceKor;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("IN_PROGRESS")
    private Status status;

    @Builder
    public Reservation(Long id, User user, Accommodation accommodation, Room room, String enquiry, LocalDate checkInDate, LocalDate checkOutDate, String price, int priceKor, Status status, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.id = id;
        this.user = user;
        this.accommodation = accommodation;
        this.room = room;
        this.enquiry = enquiry;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.price = price;
        this.priceKor = priceKor;
        this.status = status;
    }

    public static Reservation newReservation(User user, Accommodation accommodation , Room room, PaymentReqDto dto){
        return Reservation.builder()
                .user(user)
                .accommodation(accommodation)
                .room(room)
                .enquiry(dto.getEnquiry())
                .checkInDate(dto.getCheckInDate())
                .checkOutDate(dto.getCheckOutDate())
                .build();
    }

    public void status(Status result) {
        this.status = result;
    }
}
