package com.hi.domain;

import com.hi.enums.Gender;
import com.hi.enums.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UK_user_username", columnNames = {"username"}),
        @UniqueConstraint(name = "UK_user_email", columnNames = {"email"}),
        @UniqueConstraint(name = "UK_user_nickname", columnNames = {"nickname"})
})
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", columnDefinition = "bigint")
    private Long id;

    @Column(name = "username", columnDefinition = "varchar(20)", nullable = false)
    private String username;

    @Column(name = "password", columnDefinition = "varchar(255)", nullable = false)
    private String password;

    @Column(name = "email", columnDefinition = "varchar(20)", nullable = false)
    private String email;

    @Column(name = "phone_number", columnDefinition = "varchar(11)", nullable = false)
    private String phoneNumber;

    @Column(name = "postcode", columnDefinition = "varchar(5)", nullable = false)
    private String postcode;

    @Column(name = "address", columnDefinition = "varchar(50)", nullable = false)
    private String address;

    @Column(name = "detailed_address", columnDefinition = "varchar(30)", nullable = false)
    private String detailedAddress;

    @Column(name = "nickname", columnDefinition = "varchar(16)", nullable = false)
    private String nickname;

    @Column(name = "role", columnDefinition = "enum", nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("USER")
    private Role role;

    @Column(name = "last_name_kor", columnDefinition = "varchar(10)", nullable = false)
    private String lastNameKor;

    @Column(name = "first_name_kor", columnDefinition = "varchar(10)", nullable = false)
    private String firstNameKor;

    @Column(name = "last_name_eng", columnDefinition = "varchar(10)", nullable = false)
    private String lastNameEng;

    @Column(name = "first_name_eng", columnDefinition = "varchar(10)", nullable = false)
    private String firstNameEng;

    @Column(name = "gender", columnDefinition = "enum", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public User(String name,String pwd){
        this.username = name;
        this.password = pwd;
    }
}
