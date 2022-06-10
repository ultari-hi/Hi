package com.hi.service;

import com.hi.domain.JoinBoardDto;
import com.hi.domain.SearchCondition;

import java.util.List;

public interface JoinBoardService {
    JoinBoardDto read(Integer board_id);

    List<JoinBoardDto> selectPage(SearchCondition searchCondition);

    JoinBoardDto writerCheck(Integer board_id, String nickname);

    List<JoinBoardDto> readAll();

    List<JoinBoardDto> search(int type, String title);

    int write(JoinBoardDto dto);

    int modify(JoinBoardDto dto);

    int remove(Integer board_id);

    int count();

    int increaseViewCnt(Integer board_id);
}
