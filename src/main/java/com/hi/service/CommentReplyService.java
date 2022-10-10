package com.hi.service;

import com.hi.dto.CommentReplyDto;

import java.util.List;

public interface CommentReplyService {

    int count();    // 전체 대댓글 갯수

    CommentReplyDto select(Integer reply_id);   // 대댓글 조회

    List<CommentReplyDto> selectList(Integer comment_id);      // 대댓글 목록 조회

    int write(CommentReplyDto dto);         // 대댓글 생성

    int modify(CommentReplyDto dto);         // 대댓글 수정

    int remove(Integer reply_id);         // 대댓글 삭제

    int removeAll(Integer comment_id);         // 대댓글 그룹(댓글) 삭제
}
