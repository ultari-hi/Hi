package com.hi.repository;

import com.hi.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class PaymentRepository {

    private final EntityManager em;

    public Payment save(Payment payment){
        em.persist(payment);
        return payment;
    }
}
