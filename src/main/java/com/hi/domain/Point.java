package com.hi.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;
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
    @Column(name = "point_id", columnDefinition = "bigint")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "saving", columnDefinition = "integer")
    @ColumnDefault("0")
    private Integer saving;

    @Column(name = "using", columnDefinition = "integer")
    @ColumnDefault("0")
    private Integer using;

    @Column(name = "remaining", columnDefinition = "integer")
    private Integer remaining;

    @Column(name = "created_at", columnDefinition = "datetime", nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    public Point(User user, Integer saving, Integer using) {
        this.user = user;
        this.saving = saving;
        this.using = using;
        this.remaining = getRemaining(saving, using);
    }

    public Integer getRemaining(Integer saving, Integer using) {
        remaining = remaining + saving - using;
        return remaining;
    }
}
