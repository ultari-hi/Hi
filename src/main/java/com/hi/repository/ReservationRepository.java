package com.hi.repository;

import com.hi.domain.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {

    private final EntityManager em;

    public void save(Reservation reservation) {
            em.persist(reservation);
    }

    public Reservation findOne(Long reservationId) {
        return em.createQuery("select resv from Reservation resv where resv.id = :reservationId ",Reservation.class)
                .setParameter("reservationId",reservationId)
                .getSingleResult();
    }

    public List<Reservation> findAll(Long userId) {
        return em.createQuery("select resv from Reservation resv where resv.user.id = :userId", Reservation.class)
                .setParameter("userId",userId)
                .getResultList();
    }

    public List<Long> unAvailableRoom(LocalDate checkInDate, LocalDate checkOutDate){
        return em.createQuery("select distinct resv.room.id from Reservation resv " +
                "where  :checkInDate >= resv.checkInDate and :checkInDate < resv.checkOutDate " +
                "or :checkOutDate > resv.checkInDate and :checkOutDate <= resv.checkOutDate " +
                "or :checkInDate <= resv.checkInDate and :checkOutDate >= resv.checkOutDate " +
                "or :checkInDate >= resv.checkInDate and :checkOutDate <= resv.checkOutDate", Long.class)
                .setParameter("checkInDate",checkInDate)
                .setParameter("checkOutDate",checkOutDate)
                .getResultList();
    }
}
