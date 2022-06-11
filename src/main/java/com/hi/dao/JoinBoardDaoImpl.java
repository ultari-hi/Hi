package com.hi.dao;

import com.hi.domain.JoinBoardDto;
import com.hi.domain.SearchCondition;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JoinBoardDaoImpl implements JoinBoardDao{
    @Autowired
    private SqlSession session;
    private static String namespace = "com.hi.dao.JoinBoardMapper.";

    @Override
    public JoinBoardDto select(Integer board_id) {      // 동행자 게시글 상세조회
        return session.selectOne(namespace+"select", board_id);
    }

    @Override
    public JoinBoardDto writerCheck(Integer board_id, String nickname) {      // 게시글, 작성자 일치여부 확인
        Map map = new HashMap();
        map.put("board_id", board_id);
        map.put("nickname", nickname);

        return session.selectOne(namespace+"writerCheck", map);
    }

    @Override
    public List<JoinBoardDto> selectPage(SearchCondition searchCondition) {      // 게시글 페이징 처리
        return session.selectList(namespace+"selectPage", searchCondition);
    }

    @Override
    public List<JoinBoardDto> selectAll() {         // 동행자 게시글 목록 조회
        return session.selectList(namespace+"selectAll");
    }

    @Override
    public List<JoinBoardDto> searchTitle(String keyword) {         // 동행자 게시글 제목 검색
        return session.selectList(namespace+"searchTitle", keyword);
    }

    @Override
    public List<JoinBoardDto> searchTitCon(String keyword) {         // 동행자 게시글 제목+본문 검색
        return session.selectList(namespace+"searchTitCon", keyword);
    }

    // 추후 업뎃 예정
    @Override
    public List<JoinBoardDto> search(int type, String keyword, String go_with_start, String go_with_end) {         // 동행자 게시글 제목+본문+기간 검색
        Map map = new HashMap<>();
        map.put("type",type);
        map.put("keyword",keyword);
        map.put("go_with_start",go_with_start);
        map.put("go_with_end", go_with_end);
        return session.selectList(namespace+"search", map);
    }

    @Override
    public int insert(JoinBoardDto dto) {         // 동행자 게시글 생성
        System.out.println("\n DAO ======== "+dto+"\n");
        return session.insert(namespace+"insert" ,dto);
    }

    @Override
    public int update(JoinBoardDto dto) {         // 동행자 게시글 수정
        return session.update(namespace+"update" ,dto);
    }

    @Override
    public int delete(Integer board_id) {         // 동행자 게시글 삭제
        // 게시글 정보와 작성자 정보가 일치해야 삭제 진행

        return session.delete(namespace+"delete", board_id);
    }



    @Override
    public int count() {         // 전체 동행자 게시글 갯수 조회
        return session.selectOne(namespace+"count");
    }

    @Override
    public int increaseViewCnt(Integer board_id) {         // 조회수 증가
        return session.update(namespace+"increaseViewCnt", board_id);
    }



}
