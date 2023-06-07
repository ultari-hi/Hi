package com.hi.domain;

import com.hi.enums.PointType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Point extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id", columnDefinition = "bigint")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "type", columnDefinition = "enum('USING','SAVING')", nullable = false)
    @Enumerated(EnumType.STRING)
    private PointType type;

    @Column(name = "amount", columnDefinition = "int")
    private int amount;

    @Column(name = "balance", columnDefinition = "int")
    private int balance;

    @Column(name = "content", columnDefinition = "varchar(20)")
    private String content;

    @Column(name = "is_latest", columnDefinition = "boolean", nullable = false)
    private boolean isLatest;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Builder
    public Point(User user, PointType type, int amount, int balance, String content, Payment payment) {
        this.user = user;
        this.type = type;
        this.amount = amount;
        this.balance = balance;
        this.content = content;
        this.isLatest = Boolean.TRUE;
        if (balance < 0) {
            throw new IllegalArgumentException("포인트 잔액이 부족합니다.");
        }
        this.payment = payment;
    }

    //포인트 사용
    public static Point usePoint(User user, int amount, Point userPoint, Payment payment, String content){
        userPoint.setIsLatest();
        return Point.builder()
                .user(user)
                .type(PointType.USING)
                .amount(amount)
                .balance(amount - userPoint.getBalance())
                .content(content)
                .payment(payment)
                .build();
    }

    //포인트 적립
    public static Point savePoint(User user, int cashAmount, Point userPoint, Payment payment, String content) {
        userPoint.setIsLatest();
        int savingPoint = savingPointPercent(cashAmount);
        return Point.builder()
                .user(user)
                .type(PointType.SAVING)
                .amount(savingPoint)
                .balance(userPoint.getBalance() + savingPoint)
                .content(content)
                .payment(payment)
                .build();
    }

    //최신 포인트 내역 새로 쓰면서 기존 포인트 플래그를 FALSE 로 수정하는 로직
    public void setIsLatest(){
        this.isLatest = Boolean.FALSE;
    }

    public Point(User user) {
        this.user = user;
        this.type = PointType.SAVING;
        this.balance = 100000;
        this.content = "새 회원 체험 포인트";
        this.isLatest = Boolean.TRUE;
    }

    public static Point newUserPoint(User user){
        return new Point(user);
    }

    //포인트 적립 비율
    public static int savingPointPercent(int cashAmount){
        return (int) (cashAmount * 0.05);
    }

//    public static List<Point> newPoint(Payment payment, Point latestPoint) {
//
//        latestPoint.setIsLatest();
//
//        ArrayList<Point> points = new ArrayList<>();
//
//        if (payment.getPointAmount() != 0) {
//            Point pointUsing = Point.newPoint(payment.getUser(), payment.getPointAmount(), "포인트 결제", latestPoint.getBalance());
//            pointUsing.setIsLatest();
//            Point pointSaving = Point.newPoint(payment.getUser(), savingPointPercent(payment.getCashAmount()), "포인트 적립", pointUsing.getBalance());
//
//            points.set(0, pointUsing);
//            points.set(1, pointSaving);
//        } else {
//            Point pointSaving = Point.newPoint(payment.getUser(), payment.getCashAmount(), "포인트 적립", latestPoint.getBalance());
//            points.set(0, pointSaving);
//        }
//        return points;
//    }
}
