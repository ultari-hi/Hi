package com.hi.domain;

import com.hi.dto.PaymentReqDto;
import com.hi.dto.ReservationReqDto;
import com.hi.enums.Status;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name = "room_id")
    private Room room;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "payment_id")
//    private Payment payment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id")
    private Point point;

    @Column(name = "total_amount", columnDefinition = "int", nullable = false)
    private int totalAmount;

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
    public Reservation(Long id, User user, Room room /*, Payment payment*/ , Point point, int totalAmount, String enquiry, int priceKor, Status status, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.id = id;
        this.user = user;
        this.room = room;
//        this.payment = payment;
        this.point = point;
        this.totalAmount = totalAmount;
        this.enquiry = enquiry;
        this.priceKor = priceKor;
        this.status = status;
    }

    public static Reservation newReservation(User user, Room room, Point point, ReservationReqDto dto){
        return Reservation.builder()
                .user(user)
                .room(room)
                .point(point)
                .totalAmount(dto.getTotalAmount())
                .enquiry(dto.getEnquiry())
                .priceKor(room.getPriceKor())
                .status(Status.SUCCESS)
                .build();
    }

    public Reservation status(Status result) {
        this.status = result;
        return this;
    }

    public void cancel(Reservation reservation) {
        reservation.status = Status.CANCEL;
    }
}
