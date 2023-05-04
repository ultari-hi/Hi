package com.hi.service;

import com.hi.domain.EmailAuthentication;
import com.hi.dto.EmailKeyReqDto;
import com.hi.dto.user.*;
import com.hi.repository.EmailAuthenticationRepository;
import com.hi.domain.User;
import com.hi.enums.Gender;
import com.hi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@Transactional
@Slf4j
class UserServiceTest {

    @Autowired UserRepository userRepository;
    @Autowired UserService userService;
    @Autowired EmailAuthenticationRepository emailAuthenticationRepository;
    @Autowired ApplicationContext context; // 환경변수 지정하기 위해
    User user;
    UserJoinReqDto dto =
            new UserJoinReqDto("username", "testPassword", "nickname",
                    "park@naver.com", "01012345678", "12134",
                    "경기도", "A동 B호",
                    "홍", "길동", "hong",
                    "gildong", Gender.MALE, "20230101");

    @BeforeEach
    void joinTestDto() {
        // 중복 확인용 dto
        UserJoinReqDto testDto =
                new UserJoinReqDto("test", "abcd", "testNickname",
                        "test@naver.com", "01012345678", "12134",
                        "경기도", "A동 B호",
                        "홍", "길동", "hong",
                        "gildong", Gender.MALE, "20230101");
        userService.join(testDto);

        user = userRepository.findByUsername("test").orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다."));

        EmailAuthentication emailAuthentication =
                EmailAuthentication.newEmailAuthentication("test@test.com", "123123", null);
        emailAuthenticationRepository.save(emailAuthentication);
    }

    // 회원가입 성공 메시지를 따로 주어야 할 것 같다.
    @Test
    @DisplayName("회원가입 테스트 성공")
    void joinSuccess() {
        userService.join(dto);
        User joinUser =  userRepository.findByUsername("username").orElseThrow(() -> new NoResultException("findByUsername"));
        assertEquals(dto.getUsername(), joinUser.getUsername());
        assertNotEquals("fail", joinUser.getUsername());
    }

    @Test
    @DisplayName("비밀번호 암호화 수행 검증")
    void passwordEncoding() {
        assertNotEquals("testPassword", user.getPassword());
    }

    @Test
    @DisplayName("회원가입 클릭 시 아이디 중복으로 유니크 예외")
    void joinFailByUsername() {
        dto.setUsername("test");
        assertThatThrownBy(() -> userService.join(dto))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("UK_user_username")
                .hasMessageNotContaining("foreign key"); // 외래 키가 DataIntegrityViolationException 예외를 유발할 수 있어 제외
    }

    @Test
    @DisplayName("회원가입 클릭 시 이메일 중복으로 유니크 예외")
    void joinFailByEmail() {
        dto.setEmail("test@naver.com");
        assertThatThrownBy(() -> userService.join(dto))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("UK_user_email")
                .hasMessageNotContaining("foreign key"); // 외래 키가 DataIntegrityViolationException 예외를 유발할 수 있어 제외
    }

    @Test
    @DisplayName("회원가입 클릭 시 닉네임 중복으로 유니크 예외")
    void joinFailByNickname() {
        dto.setNickname("testNickname");
        assertThatThrownBy(() -> userService.join(dto))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("UK_user_nickname")
                .hasMessageNotContaining("foreign key"); // 외래 키가 DataIntegrityViolationException 예외를 유발할 수 있어 제외
    }

    @Test
    @DisplayName("닉네임 중복검사 -> 중복된 닉네임")
    void nicknameDuplicateCheckNotEmpty() {
        log.info("결과 = {}", userService.checkDuplicateNickname("testNickname"));
        assertThat(userService.checkDuplicateNickname("testNickname")).isFalse();
    }

    @Test
    @DisplayName("닉네임 중복검사 -> 가입 가능")
    void nicknameDuplicateCheckEmpty() {
        log.info("결과 = {}", userService.checkDuplicateNickname("empty"));
        assertThat(userService.checkDuplicateNickname("empty")).isTrue();
    }

    @Test
    @DisplayName("아이디 중복검사 -> 중복된 아이디")
    void usernameDuplicateCheckNotEmpty() {
        log.info("결과 = {}", userService.checkDuplicateUsername("test"));
        assertThat(userService.checkDuplicateUsername("test")).isFalse();
    }

    @Test
    @DisplayName("아이디 중복검사 -> 가입 가능")
    void usernameDuplicateCheckEmpty() {
        log.info("결과 = {}", userService.checkDuplicateUsername("Empty"));
        assertThat(userService.checkDuplicateUsername("Empty")).isTrue();
    }

    @Test
    @DisplayName("회원정보 수정")
    void update() {
        UserUpdateReqDto updateDto = new UserUpdateReqDto("12345", "testNick", "test@google.com"
                , "01087654321", "14325", "서울", "상세주소");
        String encodePassword = user.getPassword();

        //회원정보 수정 전 검증, updateDto 와 다른 것을 검증
        assertEquals(user.getPassword(), encodePassword);
        assertNotEquals(user.getNickname(), updateDto.getNickname());
        assertNotEquals(user.getEmail(), updateDto.getEmail());
        assertNotEquals(user.getPhoneNumber(), updateDto.getPhoneNumber());
        assertNotEquals(user.getPostcode(), updateDto.getPostcode());
        assertNotEquals(user.getAddress(), updateDto.getAddress());
        assertNotEquals(user.getDetailedAddress(), updateDto.getDetailedAddress());

        // 회원정보 수정
        userService.update(updateDto, user.getId());

        //회원정보 수정 후 검증, updateDto 와 같은 것을 검증
        assertNotEquals(user.getPassword(), encodePassword);
        assertEquals(user.getNickname(), updateDto.getNickname());
        assertEquals(user.getEmail(), updateDto.getEmail());
        assertEquals(user.getPhoneNumber(), updateDto.getPhoneNumber());
        assertEquals(user.getPostcode(), updateDto.getPostcode());
        assertEquals(user.getAddress(), updateDto.getAddress());
        assertEquals(user.getDetailedAddress(), updateDto.getDetailedAddress());
    }

    @Test
    @DisplayName("회원탈퇴")
    void delete() {
        assertThat(userService.delete(user.getId())).isEqualTo("success");
        assertThat(userRepository.findById(user.getId())).isEmpty();
    }

    @Test
    @DisplayName("난수 생성")
    void createKey() {
        String key1 = userService.randomNumberGenerate();
        String key2 = userService.randomNumberGenerate();
        assertThat(key1).isNotEmpty();
        assertThat(key2).isNotEmpty();
        assertThat(key1).isNotEqualTo(key2);
    }

//    @Test
//    @DisplayName("이메일 인증 메일 전송")
//    void sendEmailKeySuccess() {
//        Environment environment = context.getEnvironment();
//        assertThat(userService.sendKey(environment.getProperty("EMAIL"))).isEqualTo("인증번호 전송");
//    }

//    @Test
//    @DisplayName("이메일 인증 메일 전송 실패, 올바르지 않은 양식")
//    void sendEmailKeyFail() {
//        Environment environment = context.getEnvironment();
//        assertThatThrownBy(() -> userService.sendKey(environment.getProperty("EMAIL")+"fail."))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessageContaining("인증코드 전송 실패");
//    }

    @Test
    @DisplayName("이메일 인증 성공")
    void checkEmailSuccess() {
        EmailKeyReqDto dto = new EmailKeyReqDto("test@test.com", "123123");
        assertThat(userService.checkEmailKey(dto)).isEqualTo("인증 완료");
    }

    @Test
    @DisplayName("이메일 인증, 인증번호 불일치")
    void emailCheckingKey() {
        EmailKeyReqDto dto = new EmailKeyReqDto("test@test.com", "123120");  // 123123
        assertThat(userService.checkEmailKey(dto)).isEqualTo("인증번호가 틀립니다.");
    }


    @Test
    @DisplayName("이메일 인증, 인증번호 요청 안했을 시 메시지")
    void notSendEmail() {
        EmailKeyReqDto dto = new EmailKeyReqDto("empty@empty.com", "123123");
        assertThat(userService.checkEmailKey(dto)).isEqualTo("인증번호를 요청해주세요");
    }

    @Test
    @DisplayName("아이디 찾기 성공")
    void findUsernameSuccess() {
        FindUsernameReqDto findUsernameReqDto = new FindUsernameReqDto();
        findUsernameReqDto.setEmail("test@naver.com");
        findUsernameReqDto.setBirthDate("20230101");
        assertThat(userService.findUsername(findUsernameReqDto)).isEqualTo("test");
    }

    @Test
    @DisplayName("아이디 찾기 실패")
    void findUsernameFail() {
        FindUsernameReqDto findUsernameReqDto = new FindUsernameReqDto();
        findUsernameReqDto.setEmail("empty@naver.com");
        findUsernameReqDto.setBirthDate("20230101");
        assertThatThrownBy(() -> userService.findUsername(findUsernameReqDto))
                .isInstanceOf(NoResultException.class)
                .hasMessageContaining("회원정보를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("비밀번호 찾기, 회원정보 일치")
    void findPasswordSuccess() {
        FindPasswordReqDto findPasswordReqDto = new FindPasswordReqDto();
        findPasswordReqDto.setUsername("test");
        findPasswordReqDto.setEmail("test@naver.com");
        findPasswordReqDto.setBirthDate("20230101");
        assertThat(userService.findPassword(findPasswordReqDto)).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("비밀번호 찾기, 회원정보 불일치")
    void findPasswordFail() {
        FindPasswordReqDto findPasswordReqDto = new FindPasswordReqDto();
        findPasswordReqDto.setUsername("test");
        findPasswordReqDto.setEmail("fail");
        findPasswordReqDto.setBirthDate("20230101");
        assertThatThrownBy(() -> userService.findPassword(findPasswordReqDto))
                .isInstanceOf(NoResultException.class)
                .hasMessageContaining("회원정보를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("비밀번호 찾기 성공 후 변경")
    void changePassword() {
        ChangePasswordReqDto changePasswordReqDto = new ChangePasswordReqDto(user.getId(), "123123");

        String beforePassword = user.getPassword();

        assertThat(userService.changePassword(changePasswordReqDto)).isEqualTo("success");

        String afterPassword = user.getPassword();

        assertThat(beforePassword).isNotEqualTo(afterPassword);
        assertThat(afterPassword).isNotEqualTo("123123");
    }
}