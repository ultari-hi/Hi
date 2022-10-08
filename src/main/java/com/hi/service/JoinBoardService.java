package com.hi.service;

import com.hi.dto.JoinBoardDto;
import com.hi.domain.SearchCondition;

import java.util.List;

public interface JoinBoardService {
    JoinBoardDto read(Integer board_id);

    List<JoinBoardDto> selectPage(SearchCondition searchCondition);

    JoinBoardDto writerCheck(Integer board_id, String nickname);

//    List<JoinBoardDto> readAll();

    List<JoinBoardDto> search(String region, String title,String go_with_start, String go_with_end, SearchCondition searchCondition);

    int write(JoinBoardDto dto);

    int modify(JoinBoardDto dto);

    int remove(Integer board_id);

    int count();

    int searchCount(String region, String title, String go_with_start, String go_with_end);

    int increaseViewCnt(Integer board_id);
}
