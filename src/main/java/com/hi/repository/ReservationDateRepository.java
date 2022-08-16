package com.hi.repository;

import com.hi.domain.ReservationDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationDateRepository {

    private final EntityManager em;

    public void save(ReservationDate reservationDate){
        em.persist(reservationDate);
    }

    public void saveAll(List<ReservationDate> dates) {
        dates.forEach(this::save);
    }
}
