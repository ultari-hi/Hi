package com.hi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor // @Data에서 제대로 적용이 안되서 추가
@Data
public class SearchCondition {
    private Integer page = 1;
    private Integer pageSize = 10;  // 페이지 당 출력할 게시글 수

    public Integer getOffset() {
        return (page-1) * pageSize;
    }
}



