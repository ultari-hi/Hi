package com.hi.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TmpDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tmp_date_id", columnDefinition = "bigint")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Column(name = "date", columnDefinition = "date", nullable = false)
    private LocalDate date;

    public TmpDate(Payment payment, LocalDate date) {
        this.payment = payment;
        this.date = date;
    }

    public static TmpDate newTmpDate(Payment payment, LocalDate date){
        return new TmpDate(payment, date);
    }
}
