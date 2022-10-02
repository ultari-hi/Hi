package com.hi.dao;

import com.hi.domain.CommentDto;
import com.hi.domain.CommentReplyDto;

import java.util.List;

public interface CommentReplyDao {

    int count();    // 전체 대댓글 갯수

    CommentReplyDto select(Integer reply_id);   // 대댓글 조회

    List<CommentReplyDto> selectList(Integer comment_id);      // 대댓글 목록 조회

    int insert(CommentReplyDto dto);         // 대댓글 생성

    int update(CommentReplyDto dto);        // 대댓글 수정

    int delete(Integer reply_id);         // 대댓글 삭제
}
