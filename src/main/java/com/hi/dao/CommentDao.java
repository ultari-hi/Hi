package com.hi.dao;

import com.hi.domain.CommentDto;

import java.util.List;

public interface CommentDao {
    // 전체 댓글 갯수
    int count();   // 댓글 조회

    CommentDto select(Integer comment_id);      // 댓글 목록 조회

    List<CommentDto> selectList();         // 댓글 생성

    int insert(CommentDto dto);         // 댓글 수정

    int update(CommentDto dto);         // 댓글 삭제

    int delete(Integer board_id);
}
