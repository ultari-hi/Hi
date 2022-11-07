package com.hi.repository;

import com.hi.domain.SendEmailData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class SendEmailDataRepository {

    private final EntityManager em;

    public SendEmailData findData(){
        return em.createQuery("select data from SendEmailData data", SendEmailData.class)
                .getSingleResult();
    }
}
