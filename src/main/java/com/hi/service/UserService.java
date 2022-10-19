package com.hi.service;

import com.hi.domain.Point;
import com.hi.domain.User;
import com.hi.dto.UserJoinReqDto;
import com.hi.dto.PaymentResDto;
import com.hi.dto.UserUpdateReqDto;
import com.hi.repository.PointRepository;
import com.hi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PointRepository pointRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //유저와 포인트 조회
    public PaymentResDto findUserData(Long userId){
        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        Point point = pointRepository.findBalance(user);
        return new PaymentResDto(user, point);
    }

    //회원가입
    public void join(UserJoinReqDto dto){
        dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        User user = User.newUser(dto);
        userRepository.save(user);
    }

    //회원정보 수정
    public void update(UserUpdateReqDto dto, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        user.modify(dto);
    }

    //회원탈퇴
    public void delete(Long id){
        userRepository.delete(id);
    }

    public boolean nicknameDuplicateCheck(String nickname) {
        return userRepository.findNickname(nickname).isEmpty();
    }
}
