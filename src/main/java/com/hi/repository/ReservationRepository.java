package com.hi.repository;

import com.hi.domain.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {

    private final EntityManager em;

    public Reservation save(Reservation reservation) {
        em.persist(reservation);
        return reservation;
    }

    public Optional<Reservation> findById(Long reservationId) {
        List<Reservation> reservations = (em.createQuery("select resv from Reservation resv" +
                        " where resv.id = :reservationId" +
                        " and resv.status = 'SUCCESS'", Reservation.class)
                .setParameter("reservationId", reservationId)
                .getResultList());

        return reservations.stream().findAny();
    }

    public List<Reservation> findHistoryByUserId(Long userId) {
        return em.createQuery("select resv from Reservation resv join Room r on resv.room = r" +
                        " where resv.user.id = :userId", Reservation.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
