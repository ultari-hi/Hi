package com.hi.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class EmailAuthentication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_authentication_id", columnDefinition = "bigint")
    private Long id;

    @Column(name = "email", columnDefinition = "varchar(50)", nullable = false)
    private String email;

    @Column(name = "check_num", columnDefinition = "varchar(6)", nullable = false)
    private String checkNum;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "is_latest", columnDefinition = "boolean", nullable = false)
    private Boolean isLatest;

    public EmailAuthentication(String email, String checkNum) {
        this.email = email;
        this.checkNum = checkNum;
        this.isLatest = Boolean.TRUE;
    }

    public static EmailAuthentication newEmailAuthentication(String email, String checkNum, EmailAuthentication beforeData) {
        if (beforeData != null){
            beforeData.isLatest = Boolean.FALSE;
        }
        return new EmailAuthentication(email, checkNum);
    }

    public void setIsLatest(Boolean isLatest){
        this.isLatest = isLatest;
    }
}
