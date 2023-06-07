package com.hi.repository;

import com.hi.domain.Payment;
import com.hi.domain.TmpDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TmpDateRepository {

    private final EntityManager em;

    public void save(TmpDate tmpDate){
        em.persist(tmpDate);
    }

    public void saveAll(List<TmpDate> tmpDates) {
        tmpDates.forEach(this::save);
    }

    public List<LocalDate> findAllByPayment(Payment payment){
        return em.createQuery("select t.date from TmpDate t where t.payment = :payment", LocalDate.class)
                .setParameter("payment", payment)
                .getResultList();
    }
}
