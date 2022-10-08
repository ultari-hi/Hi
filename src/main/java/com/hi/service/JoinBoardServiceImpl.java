package com.hi.service;

import com.hi.dao.JoinBoardDaoImpl;
import com.hi.domain.JoinBoardDto;
import com.hi.domain.SearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JoinBoardServiceImpl implements JoinBoardService{
    @Autowired
    JoinBoardDaoImpl joinBoardDaoImpl;

    @Override
    public JoinBoardDto read(Integer board_id) {      // 동행자 게시글 상세조회
        return joinBoardDaoImpl.select(board_id);
    }

    @Override
    public List<JoinBoardDto> selectPage(SearchCondition searchCondition){
        return joinBoardDaoImpl.selectPage(searchCondition);
    }

    public JoinBoardDto writerCheck(Integer board_id, String nickname) {      // 게시글, 작성자 일치여부 확인
        return joinBoardDaoImpl.writerCheck(board_id, nickname);
    }

//   @Override
//    public List<JoinBoardDto> readAll() {         // 동행자 게시글 목록 조회
//        return joinBoardDaoImpl.selectAll();
//    }

    @Override
    public List<JoinBoardDto> search(String region, String title, String go_with_start, String go_with_end, SearchCondition searchCondition){
//        if(type==1) return joinBoardDaoImpl.searchTitle(keyword);
//        else if (type==2) return joinBoardDaoImpl.searchTitCon(keyword);
//        else return joinBoardDaoImpl.search(type, keyword, go_with_start, go_with_end);
        return joinBoardDaoImpl.search(region, title, go_with_start, go_with_end,searchCondition);
    }

    @Override
    public int write(JoinBoardDto dto) {         // 동행자 게시글 생성
        return joinBoardDaoImpl.insert(dto);
    }

    @Override
    public int modify(JoinBoardDto dto) {         // 동행자 게시글 수정
        return joinBoardDaoImpl.update(dto);
    }

    @Override
    public int remove(Integer board_id) {         // 동행자 게시글 삭제
        // 게시글 정보와 작성자 정보가 일치해야 삭제 진행
        return joinBoardDaoImpl.delete(board_id);
    }

    @Override
    public int count() {         // 전체 동행자 게시글 갯수 조회
        return joinBoardDaoImpl.count();
    }

    @Override
    public int searchCount(String region, String title, String go_with_start, String go_with_end) {
        return joinBoardDaoImpl.searchCount(region, title, go_with_start, go_with_end);
    }

    @Override
    public int increaseViewCnt(Integer board_id) {         // 조회수 증가
        return joinBoardDaoImpl.increaseViewCnt(board_id);
    }
}