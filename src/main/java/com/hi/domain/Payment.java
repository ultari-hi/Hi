package com.hi.domain;

import com.hi.dto.PaymentReqDto;
import com.hi.enums.Status;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Payment extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Column(name = "total_amount", columnDefinition = "integer", nullable = false)
    private Integer totalAmount;

    @Column(name = "point_pay", columnDefinition = "integer", nullable = false)
    private Integer pointPay;

    @Column(name = "cash_pay", columnDefinition = "integer", nullable = false)
    private Integer cashPay;

    @Column(name = "method", columnDefinition = "varchar(10)", nullable = false)
    private String method;

    @Column(name = "status", columnDefinition = "enum", nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("IN_PROGRESS")
    private Status status;

    public Payment(Long id, User user, Reservation reservation, Integer totalAmount, Integer pointPay, Integer cashPay, String method, Status status) {
        this.id = id;
        this.user = user;
        this.reservation = reservation;
        this.totalAmount = totalAmount;
        this.pointPay = pointPay;
        this.cashPay = cashPay;
        this.method = method;
        this.status = status;
    }

    public Payment(PaymentReqDto dto, User user, Reservation reservation){
        this.user = user;
        this.reservation = reservation;
        this.totalAmount = dto.getTotalAmount();
        this.pointPay = dto.getPointPay();
        this.cashPay = dto.getCashPay();
        this.method = dto.getMethod();
    }

    public static Payment newPayment(PaymentReqDto dto, User user, Reservation reservation){
        return new Payment(dto, user, reservation);
    }

    public void status(Status result){
        this.status = result;
    }
}
