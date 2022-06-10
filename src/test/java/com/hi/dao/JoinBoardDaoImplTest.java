package com.hi.dao;

import com.hi.domain.JoinBoardDto;
import com.hi.domain.SearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class JoinBoardDaoImplTest {
    @Autowired
    JoinBoardDaoImpl joinBoardDao;

    @Test
    public void insertTestData() throws Exception{
        for(int i=41;i<=42;i++){
            String nickname = (i%2==0?"sujin":"chan");
//            String nickname = (i%2==0?"sujin":"sangwon");
//            JoinBoardDto boardDto = new JoinBoardDto("title"+i,"파리", LocalDate.of(2022,05,17),LocalDate.of(2022,05,19),"content"+(i+100),"kkchan",2);
            JoinBoardDto boardDto = new JoinBoardDto("title"+i,"파리", "2022-05-17","2022-05-19","content"+(i+100),nickname,2);
            joinBoardDao.insert(boardDto);
        }
    }

    @Test
    void select() {
        JoinBoardDto joinBoardDto = joinBoardDao.select(1);
        assertTrue(joinBoardDao.select(1).equals(joinBoardDto));
        System.out.println(joinBoardDto);
    }

    @Test
    void selectPage() {
        SearchCondition searchCondition = new SearchCondition(1,10,"","");
        List<JoinBoardDto> list = joinBoardDao.selectPage(searchCondition);
        System.out.println(list);
        assertTrue(list.size()==10);
    }

    @Test
    void searchTitle(){
        assertTrue(joinBoardDao.searchTitle("하나").size()==2);
    }

    @Test
    void searchTitCon(){
        assertTrue(joinBoardDao.searchTitCon("집").size()==3);
    }

    @Test
    void insert() {
        JoinBoardDto boardDto = new JoinBoardDto("title"+20,"파리", "2022-05-17","2022-05-19","content"+100,"kkchan",2);
        assertTrue(joinBoardDao.insert(boardDto)==1);
    }

    @Test
    void update() {
        JoinBoardDto boardDto = new JoinBoardDto("title"+11,"파리", "2022-05-17","2022-05-19","content"+111,"kkchan",2);
        boardDto.setBoard_id(8);
        assertTrue(joinBoardDao.update(boardDto)==1);
    }

    @Test
    void delete() {
        assertTrue(joinBoardDao.delete(5)==1);
    }

    @Test
    void count() {
        assertTrue(joinBoardDao.count()==49);
    }

    @Test
    void increaseViewCnt() {

    }
}