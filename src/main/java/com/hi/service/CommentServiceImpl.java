package com.hi.service;

import com.hi.dao.CommentDaoImpl;
import com.hi.domain.CommentDto;
import com.hi.domain.JoinBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentDaoImpl commentDaoImpl;

    @Override   // 전체 댓글 갯수
    public int count() {return commentDaoImpl.count();}

    @Override   // 댓글 조회
    public CommentDto select(Integer comment_id) {      // 동행자 게시글 상세조회
        return commentDaoImpl.select(comment_id);
    }

    @Override      // 댓글 목록 조회
    public List<CommentDto> selectList(Integer board_id) {return commentDaoImpl.selectList(board_id);}

    @Override         // 댓글 생성
    public int write(CommentDto dto) {return commentDaoImpl.insert(dto);}

    @Override         // 댓글 수정
    public int modify(CommentDto dto) {return commentDaoImpl.update(dto);}

    @Override         // 댓글 삭제
    public int remove(Integer comment_id) {return commentDaoImpl.delete(comment_id);}
}
