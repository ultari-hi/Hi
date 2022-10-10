package com.hi.service;

import com.hi.dto.CommentDto;

import java.util.List;

public interface CommentService {
    // 전체 댓글 갯수
    int count();   // 댓글 조회

    CommentDto select(Integer comment_id);      // 댓글 목록 조회

    List<CommentDto> selectList(Integer board_id);         // 댓글 생성

    int write(CommentDto dto);         // 댓글 수정

    int modify(CommentDto dto);         // 댓글 삭제

    int remove(Integer comment_id);
}
