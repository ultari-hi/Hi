package com.hi.service;

import com.hi.domain.EmailAuthentication;
import com.hi.domain.Point;
import com.hi.domain.SendEmailForm;
import com.hi.domain.User;
import com.hi.dto.*;
import com.hi.dto.user.*;
import com.hi.repository.EmailAuthenticationRepository;
import com.hi.repository.PointRepository;
import com.hi.repository.SendEmailFormRepository;
import com.hi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.NoResultException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PointRepository pointRepository;
    private final EmailAuthenticationRepository emailAuthenticationRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender javaMailSender;
    private final SendEmailFormRepository sendEmailFormRepository;

    //유저와 포인트 조회
    public PaymentResDto findUserData(Long userId){
        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        Point point = pointRepository.findLatest(user);
        return new PaymentResDto(user, point);
    }

    //회원가입
    public void join(UserJoinReqDto dto){
        // 비밀번호 암호화
        dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        User user = User.newUser(dto);
        userRepository.save(user);

//        Point point = Point.newUserPoint(user);
//        pointRepository.save(point);
    }

    //회원정보 수정
    public void update(UserUpdateReqDto dto, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        user.modify(dto);
    }

    //회원탈퇴
    public String delete(Long id){
        userRepository.delete(id);
        if (userRepository.findById(id).isEmpty()) {
            return "success";
        }
        return "fail";
    }

    //아이디 중복검사
    public boolean checkDuplicateUsername(String username) {
        return userRepository.findUsername(username).isEmpty();
    }

    //닉네임 중복검사
    public boolean checkDuplicateNickname(String nickname) {
        return userRepository.findNickname(nickname).isEmpty();
    }

    //이메일 인증번호 발송
    public String sendKey(String email) {
        String result;
        if (userRepository.checkDuplicateEmail(email).isEmpty()) {
            //난수 생성
            String checkNum = randomNumberGenerate();

            //DB에 저장된 이메일 양식
            SendEmailForm sendEmailForm = sendEmailFormRepository.findData();

            MimeMessage message = javaMailSender.createMimeMessage();
            try {
                message.addRecipients(Message.RecipientType.TO, email);
                message.setFrom(new InternetAddress(sendEmailForm.getSender(), sendEmailForm.getSenderCustom(), "utf-8"));
                message.setSubject(sendEmailForm.getTitle());
                message.setText(sendEmailForm.getNewContent(checkNum), "utf-8", "html");
            } catch (MessagingException e) {
                throw new IllegalArgumentException("인증코드 전송 실패: " + e.getMessage());
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("인터넷 주소 인코딩 실패: " + e.getMessage());
            }
            javaMailSender.send(message);

            EmailAuthentication beforeData = emailAuthenticationRepository.findByEmail(email);
            EmailAuthentication emailAuthentication = EmailAuthentication.newEmailAuthentication(email, checkNum, beforeData);
            emailAuthenticationRepository.save(emailAuthentication);
            result = "인증번호 전송";
        } else {
            result = "이미 가입 되어있는 이메일입니다.";
        }
        return result;
    }

    //난수 생성 메서드
    public String randomNumberGenerate() {
        Random random = new Random();
        StringBuilder randomNum = new StringBuilder();
        for (int i=0; i<6; i++){
            randomNum.append(random.nextInt(10));
        }
        return randomNum.toString();
    }

    //이메일 인증번호 확인
    public String checkEmailKey(EmailKeyReqDto dto){
        EmailAuthentication emailAuthentication = emailAuthenticationRepository.findByEmail(dto.getEmail());

        if (emailAuthentication == null){
            return "인증번호를 요청해주세요";
        }

        String result = null;

        if (emailAuthentication.getEmail().equals(dto.getEmail())
                & emailAuthentication.getCheckNum().equals(dto.getCheckNum())){
            if (LocalDateTime.now().isAfter(emailAuthentication.getCreatedAt().plusMinutes(10))){
                result = "유효시간 초과";
            } else {
                result = "인증 완료";
                emailAuthentication.setIsLatest(Boolean.FALSE);
            }
        } else if (emailAuthentication.getEmail().equals(dto.getEmail())
                & !emailAuthentication.getCheckNum().equals(dto.getCheckNum()) ){
            result = "인증번호가 틀립니다.";
        }
        return result;
    }

    //아이디 찾기
    public String findUsername(FindUsernameReqDto dto){
        return userRepository.findUsernameByEmail(dto)
                .orElseThrow(()-> new NoResultException("회원정보를 찾을 수 없습니다."));
    }

    //비밀번호 찾기 회원정보 확인
    public Long findPassword(FindPasswordReqDto dto){
        User user = userRepository.findPassword(dto)
                .orElseThrow(()-> new NoResultException("회원정보를 찾을 수 없습니다."));
        return user.getId();
    }

    //비밀번호 찾기 후 비밀번호 변경
    public String changePassword(ChangePasswordReqDto dto){
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(()-> new IllegalArgumentException("회원정보를 찾을 수 없습니다."));

        user.modifyPassword(dto.getPassword());

        return "success";
    }
}
