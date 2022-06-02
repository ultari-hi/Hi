package com.hi.dao;

import com.hi.domain.JoinBoardDto;

import java.util.List;

public interface JoinBoardDao {
    JoinBoardDto select(Integer board_id);

    JoinBoardDto writerCheck(Integer board_id, String nickname);

    List<JoinBoardDto> selectAll();

    int insert(JoinBoardDto dto);

    int update(JoinBoardDto dto);

    int delete(Integer board_id);

    int count();

    int increaseViewCnt(Integer board_id) ;

}
