package com.hi.dao;

import com.hi.domain.JoinBoardDto;
import com.hi.domain.SearchCondition;

import java.util.List;

public interface JoinBoardDao {
    JoinBoardDto select(Integer board_id);

    List<JoinBoardDto> selectPage(SearchCondition searchCondition);

    JoinBoardDto writerCheck(Integer board_id, String nickname);

    List<JoinBoardDto> selectAll();

    List<JoinBoardDto> searchTitle(String keyword);

    List<JoinBoardDto> searchTitCon(String keyword);

    int insert(JoinBoardDto dto);

    int update(JoinBoardDto dto);

    int delete(Integer board_id);

    int count();

    int increaseViewCnt(Integer board_id) ;

}
