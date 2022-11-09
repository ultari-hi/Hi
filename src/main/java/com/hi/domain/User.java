package com.hi.domain;

import com.hi.dto.user.UserJoinReqDto;
import com.hi.dto.user.UserUpdateReqDto;
import com.hi.enums.Gender;
import com.hi.enums.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "role", columnDefinition = "enum('ADMIN', 'MANAGER', 'USER')", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "last_name_kor", columnDefinition = "varchar(10)", nullable = false)
    private String lastNameKor;

    @Column(name = "first_name_kor", columnDefinition = "varchar(10)", nullable = false)
    private String firstNameKor;

    @Column(name = "last_name_eng", columnDefinition = "varchar(10)", nullable = false)
    private String lastNameEng;

    @Column(name = "first_name_eng", columnDefinition = "varchar(10)", nullable = false)
    private String firstNameEng;

    @Column(name = "gender", columnDefinition = "enum('MALE', 'FEMALE')", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "birth_date", columnDefinition = "varchar(8)", nullable = false)
    private String birthDate;

    @Builder
    public User(String username, String password, String nickname, String email, String phoneNumber, String postcode, String address, String detailedAddress, String lastNameKor, String firstNameKor, String lastNameEng, String firstNameEng, Gender gender, String birthDate) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.postcode = postcode;
        this.address = address;
        this.detailedAddress = detailedAddress;
        this.lastNameKor = lastNameKor;
        this.firstNameKor = firstNameKor;
        this.lastNameEng = lastNameEng;
        this.firstNameEng = firstNameEng;
        this.gender = gender;
        this.role = Role.USER;
        this.birthDate = birthDate;
    }

    public static User newUser(UserJoinReqDto dto) {
        return User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .postcode(dto.getPostcode())
                .address(dto.getAddress())
                .detailedAddress(dto.getDetailedAddress())
                .lastNameKor(dto.getLastNameKor())
                .firstNameKor(dto.getFirstNameKor())
                .lastNameEng(dto.getLastNameEng())
                .firstNameEng(dto.getFirstNameEng())
                .gender(dto.getGender())
                .birthDate(dto.getBirthDate())
                .build();
    }

    public String getRole() {
        return role.name();
    }

    public void modify(UserUpdateReqDto dto) {
        this.password = dto.getPassword();
        this.nickname = dto.getNickname();
        this.email = dto.getEmail();
        this.phoneNumber = dto.getPhoneNumber();
        this.postcode = dto.getPostcode();
        this.address = dto.getAddress();
        this.detailedAddress = dto.getDetailedAddress();
    }

    //비밀번호 찾기 후 비밀번호 변경
    public void modifyPassword(String password){
        this.password = password;
    }
}
