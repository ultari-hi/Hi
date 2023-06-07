package com.hi.repository;

import com.hi.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentRepository {

    private final EntityManager em;

    public Payment save(Payment payment){
        em.persist(payment);
        return payment;
    }

    public Optional<Payment> findById(Long paymentId){
        try {
            return Optional.ofNullable(em.createQuery("select p from Payment p" +
                            " where p.id = :paymentId",Payment.class)
                    .setParameter("paymentId", paymentId)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
