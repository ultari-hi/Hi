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

    @Column(name = "total_amount")
    private Integer totalAmount;

    @Column(name = "point_pay")
    @ColumnDefault("0")
    private Integer pointPay;

    @Column(name = "cash_pay")
    private Integer cashPay;

    @Column(name = "method", columnDefinition = "char(10)")
    private String method;

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

    public void status(String result){
        if (result.equals("success")) {
            this.status = Status.SUCCESS;
        } else if (result.equals("fail")) {
            this.status = Status.FAIL;
        } else {
            System.out.print("오류 메세지");
        }
    }
}
