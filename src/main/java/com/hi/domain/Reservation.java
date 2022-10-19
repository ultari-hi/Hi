package com.hi.domain;

import com.hi.dto.PaymentReqDto;
import com.hi.enums.Status;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Reservation extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id", columnDefinition = "bigint")
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Column(name = "enquiry", columnDefinition = "text")
    private String enquiry;

    @Column(name = "price_kor", columnDefinition = "int", nullable = false)
    private int priceKor;

    @Column(name = "status", columnDefinition = "enum('SUCCESS', 'FAIL', 'IN_PROGRESS', 'CANCEL')", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "reservation")
    private final List<ReservationDate> reservationDates = new ArrayList<>();

    @Builder
    public Reservation(Long id, User user, Accommodation accommodation, Room room, Payment payment, String enquiry, int priceKor, Status status, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.id = id;
        this.user = user;
        this.accommodation = accommodation;
        this.room = room;
        this.payment = payment;
        this.enquiry = enquiry;
        this.priceKor = priceKor;
        this.status = status;
    }

    public static Reservation newReservation(User user, Accommodation accommodation , Room room, PaymentReqDto dto){
        return Reservation.builder()
                .user(user)
                .accommodation(accommodation)
                .room(room)
                .enquiry(dto.getEnquiry())
                .priceKor(room.getPriceKor())
                .build();
    }

    public Reservation status(Status result) {
        this.status = result;
        return this;
    }
}
