package com.hi.domain;

import com.hi.enums.Status;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToOne(mappedBy = "reservation")
    private Payment payment;

    private String enquiry;

    @CreatedDate
    @Column(name = "check_in_date")
    private LocalDate checkInDate;

    @CreatedDate
    @Column(name = "check_out_date")
    private LocalDate checkOutDate;

    private int price;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("IN_PROGRESS")
    private Status status;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;


    public Reservation(User user, Accommodation accommodation, Room room, Payment payment){
        this.user = user;
        this.accommodation = accommodation;
        this.room = room;
        this.payment = payment;
        this.status = Status.IN_PROGRESS;
    }

}
