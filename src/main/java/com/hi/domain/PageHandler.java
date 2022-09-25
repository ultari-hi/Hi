package com.hi.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)  // 응답 시 null인 값을 자동으로 제거
public class PageHandler {
    private SearchCondition sc; //page, pageSize

    private int boardTotCount; // 출력되는 총 게시글 갯수
    private int boardCurCount; // 현재 출력되는 게시글 갯수
//    private int naviSize = 10; // 페이지 네비게이션의 크기
    private int totalPage; // 총 페이지 갯수
//    private int beginPage; // 페이지 호출 당 첫번째 페이지 번호
//    private int endPage; // 페이지 호출 당 마지막 페이지 번호
    private boolean isEnd = false;  // 마지막 페이지 여부

    public PageHandler(int boardCount, SearchCondition sc){
        this.boardTotCount = boardCount;
        this.sc = sc;

        doPaging(boardCount, sc);
    }

    // 페이지 계산 메서드
    public void doPaging(int boardCount, SearchCondition sc){
        this.totalPage = (int)Math.ceil(boardCount / (double)sc.getPageSize()); // 총 페이지 수 계산, 남는 게시물이 있을 수 있기에 페이지 갯수 올림처리
//        this.beginPage = (sc.getPage()-1)/sc.getPageSize() * sc.getPageSize() + 1; // 페이지의 맨 앞 네비게이션 숫자를 위해 현재 페이지에서 1의 자리를 날려주고 1을 다시 붙인다.
        this.isEnd = (sc.getPage() >= totalPage); // 현재 페이지 번호와 총 페이지 갯수가 같으면 마지막 페이지
    }

}
