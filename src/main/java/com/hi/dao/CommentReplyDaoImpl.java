package com.hi.dao;

import com.hi.domain.CommentReplyDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentReplyDaoImpl implements CommentReplyDao{
    @Autowired
    private SqlSession session;
    private static String namespace = "com.hi.dao.CommentReplyMapper.";

    @Override   // 전체 대댓글 갯수
    public int count() {return session.selectOne(namespace+"count");}

    @Override   // 대댓글 조회
    public CommentReplyDto select(Integer reply_id) {      // 동행자 게시글 상세조회
        return session.selectOne(namespace+"select", reply_id);
    }

    @Override      // 대댓글 목록 조회
    public List<CommentReplyDto> selectList(Integer comment_id) {return session.selectList(namespace+"selectList", comment_id);}

    @Override         // 대댓글 생성
    public int insert(CommentReplyDto dto) {return session.insert(namespace+"insert" ,dto);}

    @Override         // 대댓글 수정
    public int update(CommentReplyDto dto) {return session.update(namespace+"update" ,dto);}

    @Override         // 대댓글 삭제
    public int delete(Integer reply_id) {return session.delete(namespace+"delete", reply_id);}

}
