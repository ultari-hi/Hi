package com.hi.repository;

import com.hi.domain.Reservation;
import com.hi.domain.ReservationDate;
import com.hi.domain.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationDateRepository {

    private final EntityManager em;

    public void save(ReservationDate reservationDate){
        em.persist(reservationDate);
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void saveAll(List<ReservationDate> dates) {
        dates.forEach(this::save);
    }

    public List<Long> unAvailableRooms(List<LocalDate> selectDates){
        return em.createQuery("select distinct resvDate.room.id from ReservationDate resvDate" +
                        " where resvDate.date in :selectDates", Long.class)
                .setParameter("selectDates", selectDates)
                .getResultList();
    }

    public void cancelReservation(Reservation reservation) {
        em.createQuery("delete from ReservationDate resvDate where resvDate.reservation = :reservation")
                .setParameter("reservation", reservation);
    }
}
