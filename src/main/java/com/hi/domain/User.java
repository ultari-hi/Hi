package com.hi.domain;

import com.hi.enums.Gender;
import com.hi.enums.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UK_user_username", columnNames = {"username"}),
        @UniqueConstraint(name = "UK_user_email", columnNames = {"email"}),
        @UniqueConstraint(name = "UK_user_nickname", columnNames = {"nickname"})
})
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;

    private String password;

    private String email;

    @Column(name = "country_number")
    private String countryNumber;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "post_code")
    private String postCode;

    private String address;

    @Column(name = "detail_address")
    private String detailAddress;

    private String nickname;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("USER")
    private Role role;

    @Column(name = "last_name_kor")
    private String lastNameKor;

    @Column(name = "first_name_kor")
    private String firstNameKor;

    @Column(name = "last_name_eng")
    private String lastNameEng;

    @Column(name = "first_name_eng")
    private String firstNameEng;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    public User(String name,String pwd){
        this.username = name;
        this.password = pwd;
    }
}
