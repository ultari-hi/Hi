package com.hi.service;

import com.hi.domain.Point;
import com.hi.domain.User;
import com.hi.dto.PaymentResDto;
import com.hi.repository.PointRepository;
import com.hi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PointRepository pointRepository;

    public PaymentResDto findUserData(Long userId){
        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        Point point = pointRepository.findBalance(user);
        return new PaymentResDto(user, point);
    }
}
