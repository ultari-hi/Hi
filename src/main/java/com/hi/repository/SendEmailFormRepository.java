package com.hi.repository;

import com.hi.domain.SendEmailForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 * DB에 저장된 이메일 양식
 */
@Repository
@RequiredArgsConstructor
public class SendEmailFormRepository {

    private final EntityManager em;

    public SendEmailForm findData(){
        return em.createQuery("select data from SendEmailForm data", SendEmailForm.class)
                .getSingleResult();
    }
}
