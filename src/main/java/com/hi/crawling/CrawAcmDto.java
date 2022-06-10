package com.hi.crawling;

import lombok.Data;

import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)  // 응답 시 null인 값을 자동으로 제거
public class CrawAcmDto {
    private String name="";            // 숙소 이름
    private String type="";            // 객실 타입
    private String priceKor="";        // 원화 가격
    private String price="";           // 현지 가격 (유로, 달러 등)
    private String rating="";          // 리뷰점수
    private String num_of_review="";   // 리뷰 갯수
    private List<String> image=null;     // 숙소 사진
    private List<CrawRoomDto> rooms=null;              // 객실 목록
    private String introduction="";    // 숙소 소개글
    private List<String> publicFacility=null;  // 부가 시설 및 서비스
    private String breakfast="";       // 조식
    private String lunch="";           // 조식
    private String dinner="";          // 조식
    private String spot="";            // 숙소 근처 스팟
    private String role="";            // 숙소 이용 규칙
    private String paidService="";     // 유료 서비스
    private String peculiarity="";     // 기타 특이사항
    private String location="";        // 위치
    private String howCome="";         // 오시는 방법
    private String cancel="";           //취소환불규정
}
