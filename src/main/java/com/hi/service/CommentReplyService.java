package com.hi.service;

import com.hi.domain.CommentDto;
import com.hi.domain.CommentReplyDto;

import java.util.List;

public interface CommentReplyService {

    int count();    // 전체 대댓글 갯수

    CommentReplyDto select(Integer reply_id);   // 대댓글 조회

    List<CommentReplyDto> selectList(Integer comment_id);      // 대댓글 목록 조회

    int write(CommentReplyDto dto);         // 대댓글 생성

    int modify(CommentReplyDto dto);         // 대댓글 수정

    int remove(Integer reply_id);         // 대댓글 삭제
}
