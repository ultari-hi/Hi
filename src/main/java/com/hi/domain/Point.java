package com.hi.domain;

import com.hi.enums.PointType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id", columnDefinition = "bigint")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "type", columnDefinition = "enum", nullable = false)
    @Enumerated(EnumType.STRING)
    private PointType type;

    @Column(name = "amount", columnDefinition = "int")
    private int amount;

    @Column(name = "balance", columnDefinition = "int")
    private int balance;

    @Column(name = "contents", columnDefinition = "varchar(20)")
    @ColumnDefault("0")
    private String contents;

    @Column(name = "is_latest", columnDefinition = "boolean", nullable = false)
    @ColumnDefault("TRUE")
    private boolean isLatest;

    @Column(name = "created_at", columnDefinition = "datetime", nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    public Point(Long id, User user, PointType type, int amount, int balance, String contents, Boolean isLatest, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.type = type;
        this.amount = amount;
        this.balance = balance;
        this.contents = contents;
        this.isLatest = isLatest;
        this.createdAt = createdAt;
    }

    public Point(User user, PointType type, int amount, String contents, int userPointBalance) {
        this.user = user;
        this.type = type;
        this.amount = amount;
        this.contents = contents;
        this.balance = amount + userPointBalance;
    }

    public static Point newPoint(User user, int amount, String contents, int userPointBalance){
        PointType type;
        if(amount > 0) {
            type = PointType.SAVING;
        } else {
            type = PointType.USING;
        }
        return new Point(user, type, amount, contents, userPointBalance);
    }

    //최신 포인트 내역 새로 쓰면서 기존 포인트 플래그를 FALSE 로 수정하는 로직
    public void setIsLatest(){
        this.isLatest = Boolean.FALSE;
    }

    //포인트 적립 비율
    public static int savingPointAmount(int cashAmount){
        return (int) (cashAmount * 0.05);
    }

    public static List<Point> newPoint(Payment payment, Point latestPoint) {

        latestPoint.setIsLatest();

        ArrayList<Point> points = new ArrayList<Point>();

        if (payment.getPointAmount() != 0) {
            Point pointUsing = Point.newPoint(payment.getUser(), payment.getPointAmount(), "포인트 결제", latestPoint.getBalance());
            pointUsing.setIsLatest();
            Point pointSaving = Point.newPoint(payment.getUser(), savingPointAmount(payment.getCashAmount()), "포인트 적립", pointUsing.getBalance());

            points.set(0, pointUsing);
            points.set(1, pointSaving);
        } else {
            Point pointSaving = Point.newPoint(payment.getUser(), payment.getCashAmount(), "포인트 적립", latestPoint.getBalance());
            points.set(0, pointSaving);
        }
        return points;
    }
}
