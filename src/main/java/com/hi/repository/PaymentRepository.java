package com.hi.repository;

import com.hi.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentRepository {

    private final EntityManager em;

    public void save(Payment payment){
        em.persist(payment);
    }

    public Optional<Payment> findById(Long reservationId){
        return Optional.ofNullable(em.createQuery("select p from Payment p join p.reservation where p.reservation.id = :reservationId",Payment.class)
                .setParameter("reservationId",reservationId)
                .getSingleResult());
    }
}
