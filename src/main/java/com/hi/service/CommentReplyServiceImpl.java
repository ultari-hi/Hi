package com.hi.service;

import com.hi.dao.CommentReplyDaoImpl;
import com.hi.domain.CommentReplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentReplyServiceImpl implements CommentReplyService {
    @Autowired
    CommentReplyDaoImpl commentReplyDaoImpl;

    @Override   // 전체 대댓글 갯수
    public int count() {return commentReplyDaoImpl.count();}

    @Override   // 대댓글 조회
    public CommentReplyDto select(Integer reply_id) {      // 동행자 게시글 상세조회
        return commentReplyDaoImpl.select(reply_id);
    }

    @Override      // 대댓글 목록 조회
    public List<CommentReplyDto> selectList(Integer comment_id) {return commentReplyDaoImpl.selectList(comment_id);}

    @Override         // 대댓글 생성
    public int write(CommentReplyDto dto) {return commentReplyDaoImpl.insert(dto);}

    @Override         // 대댓글 수정
    public int modify(CommentReplyDto dto) {return commentReplyDaoImpl.update(dto);}

    @Override         // 대댓글 삭제
    public int remove(Integer reply_id) {return commentReplyDaoImpl.delete(reply_id);}
}
