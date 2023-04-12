//package com.hi.domain;
//
//import com.hi.dto.PaymentReqDto;
//import com.hi.enums.Status;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.ColumnDefault;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Entity
//public class Payment extends BaseTimeEntity{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "payment_id", columnDefinition = "bigint")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @Column(name = "total_amount", columnDefinition = "int", nullable = false)
//    private int totalAmount;
//
//    @Column(name = "point_amount", columnDefinition = "int", nullable = false)
//    private int pointAmount;
//
//    @Column(name = "cash_amount", columnDefinition = "int", nullable = false)
//    private int cashAmount;
//
//    @Column(name = "method", columnDefinition = "varchar(20)", nullable = false)
//    private String method;
//
//    @Column(name = "status", columnDefinition = "enum('SUCCESS', 'FAIL', 'IN_PROGRESS', 'CANCEL')", nullable = false)
//    @Enumerated(EnumType.STRING)
//    private Status status;
//
//    @OneToOne(mappedBy = "payment")
//    private Reservation reservation;
//
//    @OneToMany(mappedBy = "payment")
//    private final List<TmpDate> tmpDate = new ArrayList<>();
//
//    public Payment(Long id, User user, int totalAmount, int pointAmount, int cashAmount, String method, Status status) {
//        this.id = id;
//        this.user = user;
//        this.totalAmount = totalAmount;
//        this.pointAmount = pointAmount;
//        this.cashAmount = cashAmount;
//        this.method = method;
//        this.status = status;
//    }
//
//    public Payment(PaymentReqDto dto, User user){
//        this.user = user;
//        this.totalAmount = dto.getTotalAmount();
//        this.pointAmount = dto.getPointAmount();
//        this.cashAmount = dto.getCashAmount();
//        this.method = dto.getMethod();
//    }
//
//    public static Payment newPayment(PaymentReqDto dto, User user){
//        return new Payment(dto, user);
//    }
//
//    public Status verify(int paidAmount){
//        if(paidAmount == this.cashAmount){
//            this.status = Status.SUCCESS;
//            this.reservation.status(this.status);
//        }else {
//            this.status = Status.FAIL;
//        }
//        return this.status;
//    }
//}
