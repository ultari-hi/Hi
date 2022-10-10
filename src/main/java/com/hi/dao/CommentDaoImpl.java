package com.hi.dao;

import com.hi.dto.CommentDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDaoImpl implements CommentDao {
    @Autowired
    private SqlSession session;
    private static String namespace = "com.hi.dao.CommentMapper.";

    @Override   // 전체 댓글 갯수
    public int count() {return session.selectOne(namespace+"count");}

    @Override   // 댓글 조회
    public CommentDto select(Integer comment_id) {      // 동행자 게시글 상세조회
        return session.selectOne(namespace+"select", comment_id);
    }

    @Override      // 댓글 목록 조회
    public List<CommentDto> selectList(Integer board_id) {return session.selectList(namespace+"selectList", board_id);}

    @Override         // 댓글 생성
    public int insert(CommentDto dto) {return session.insert(namespace+"insert" ,dto);}

    @Override         // 댓글 수정
    public int update(CommentDto dto) {return session.update(namespace+"update" ,dto);}

    @Override         // 댓글 삭제
    public int delete(Integer comment_id) {return session.delete(namespace+"delete", comment_id);}

}
