package com.hi.crawling;

import lombok.Data;

@Data
public class CrawRoomDto {
    private String name="";            // 방 이름
    private String type="";            // 방 타입
    private String number_of_people=""; // 객실 정원
    private String minStay;             // 최소 이용 일
    private String priceKor="";        // 원화 가격
    private String price="";           // 현지 가격 (유로, 달러 등)
    private String priceBasis="";       // 가격 기준
    private String picture="";             // 객실 이미지
    private String facilities="";// 시설
}
