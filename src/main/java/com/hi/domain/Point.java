package com.hi.domain;

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
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ColumnDefault("0")
    private Integer saving;

    @ColumnDefault("0")
    private Integer using;

    @ColumnDefault("0")
    private Integer remaining;

    @CreatedDate
    @Column(name = "changed_date")
    private LocalDateTime changedDate;

    private LocalDate extinction;

    public Point(User user, Integer saving, Integer using, Integer remaining, LocalDateTime changedDate, LocalDate extinction) {
        this.user = user;
        this.saving = saving;
        this.using = using;
        this.remaining = remaining;
        this.changedDate = changedDate;
        this.extinction = extinction;
    }
}
