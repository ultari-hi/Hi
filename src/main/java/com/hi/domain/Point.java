package com.hi.domain;

import com.hi.enums.PointType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    public Point(Long id, User user, PointType type, int amount, int balance, String content, Boolean isLatest, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.type = type;
        this.amount = amount;
        this.balance = balance;
        this.content = content;
        this.isLatest = isLatest;
        this.createdAt = createdAt;
    }

    public Point(User user, PointType type, int amount, String content, int userPointBalance) {
        this.user = user;
        this.type = type;
        this.amount = amount;
        this.content = content;
        this.balance = amount + userPointBalance;
        this.isLatest = Boolean.TRUE;
    }

    //포인트 사용
    public static Point usePoint(User user, int amount, String content, Point userPoint){
        PointType type = PointType.USING;
        userPoint.setIsLatest();
        return new Point(user, type, -amount, content, userPoint.getBalance());
    }

    //포인트 적립
    public static Point savePoint(User user, int amount, String content, Point userPoint) {
        PointType type = PointType.SAVING;
        userPoint.setIsLatest();
        return new Point(user, type, amount, content, userPoint.getBalance());
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
    public static int savingPointAmount(int cashAmount){
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
//            Point pointSaving = Point.newPoint(payment.getUser(), savingPointAmount(payment.getCashAmount()), "포인트 적립", pointUsing.getBalance());
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
