package com.hi.service;

import com.hi.domain.JoinBoardDto;

import java.util.List;

public interface JoinBoardService {
    JoinBoardDto read(Integer board_id);

    JoinBoardDto writerCheck(Integer board_id, String nickname);

    List<JoinBoardDto> readAll();

    int write(JoinBoardDto dto);

    int modify(JoinBoardDto dto);

    int remove(Integer board_id);

    int count();

    int increaseViewCnt(Integer board_id);
}
