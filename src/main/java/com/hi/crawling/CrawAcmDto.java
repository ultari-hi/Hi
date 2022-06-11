package com.hi.crawling;

import lombok.Data;

import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)  // 응답 시 null인 값을 자동으로 제거
public class CrawAcmDto {
    private String accommodation_id="";         // 숙소 코드
    private String nameKor="null";                  // 숙소 이름
    private String nameEng="null";                  // 숙소 이름
    private String type="";                     // 객실 타입
    private String number_of_people="0";         //최대 인원
    private String price_kor="0";                // 원화 가격
    private String price="0";                    // 현지 가격 (유로, 달러 등)
    private String rating="0.0";                   // 리뷰점수
    private String num_of_review="0";            // 리뷰 갯수
    private String image="null";                    // 숙소 사진
    private List<CrawRoomDto> rooms=null;       // 객실 목록
    private String introduction="Not Found";             // 숙소 소개글
    private String publicFacility="null";           // 부가 시설 및 서비스
    private String breakfast="없음";                // 조식
    private String lunch="없음";                    // 중식
    private String dinner="없음";                   // 석식
    private String spot="null";                     // 숙소 근처 스팟
    private String role="null";                     // 숙소 이용 규칙
    private String paidService="null";              // 유료 서비스
    private String peculiarity="null";              // 기타 특이사항
    private String address="null";                  // 주소
    private String post_code="0000";            // 우편번호
    private String location="null";                 // 위치
    private String howCome="null";                  // 오시는 방법
    private String cancel="null";                   //취소환불규정
}
