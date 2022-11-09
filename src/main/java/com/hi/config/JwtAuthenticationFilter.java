package com.hi.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hi.config.auth.CustomUserDetails;
import com.hi.dto.user.UserLoginReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    //로그인 요청하면 로그인 시도를 위해 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        ObjectMapper objectMapper = new ObjectMapper();
        UserLoginReqDto userLoginReqDto = null;

        try {
            userLoginReqDto = objectMapper.readValue(request.getInputStream(), UserLoginReqDto.class);
        } catch (IOException e){
            e.printStackTrace();
        }

        //유저네임 패스워드 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginReqDto.getUsername(), userLoginReqDto.getPassword());

        //authenticate() 호출시 AuthenticationProvider 가 UserDetailsService 의 loadUserByUsername()(첫 번째 파라미터) 함수 실행
        //UserDetails 를 리턴 받아서 위의 두번째 파라미터와 UserDetails 의 getPassword() 함수로 비교
        //동일하면 Authentication 객체 만들어서 필터체인으로 리턴
        //리턴시 authentication 객체가 session 영역에 저장됨, 권한 관리를 시큐리티가 대신 해준다.
        //JWT 토큰을 사용하면 세션을 만들 이유가 없지만 권한 처리 때문에 세션에 넣는다.
        //AuthenticationProvider 의 디폴트 서비스는 UserDetailsService, 디폴트 암호화 방식은 BCryptPasswordEncoder
        return authenticationManager.authenticate(authenticationToken);
    }

    //attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행된다.
    //JWT 토큰을 만들어서 request 요청한 사용자에게 JWT 토큰을 response 해줘야 한다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject(customUserDetails.getUsername()) //토큰의 이름
                .withExpiresAt(new Date(System.currentTimeMillis()+ JwtProperties.EXPIRATION_TIME)) //토큰 만료시간
                .withClaim("id", customUserDetails.getUserId()) //토큰 데이터
                .sign(Algorithm.HMAC512(JwtProperties.SECRET)); // secret 값 세팅

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken); //헤더 추가
    }

}
