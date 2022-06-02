package com.hi.dao;

import com.hi.domain.JoinBoardDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JoinBoardDaoImplTest {
    @Autowired
    JoinBoardDaoImpl joinBoardDao;

    @Test
    public void insertTestData() throws Exception{
        for(int i=1;i<=1;i++){
            String gender = (i%2==0?"남":"여");
            String nickname = (i%2==0?"sujin":"chan");
            JoinBoardDto boardDto = new JoinBoardDto("title"+i,"파리", LocalDate.of(2022,05,17),LocalDate.of(2022,05,19),"content"+(i+100),"kkchan",2);
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
    void selectAll() {

    }

    @Test
    void insert() {
        JoinBoardDto joinBoardDto = new JoinBoardDto("title","런던",LocalDate.of(2022,05,17),LocalDate.of(2022,05,19),"치맥 동행자","kkchan",3);
        assertTrue(joinBoardDao.insert(joinBoardDto)==1);
    }

    @Test
    void update() {
        JoinBoardDto joinBoardDto = new JoinBoardDto("title","런던",LocalDate.of(2022,05,17),LocalDate.of(2022,05,19),"치맥 동행자","kkchan",3);
        joinBoardDto.setBoard_id(1);
        assertTrue(joinBoardDao.update(joinBoardDto)==1);
    }

    @Test
    void delete() {
        assertTrue(joinBoardDao.delete(5)==1);
    }

    @Test
    void count() {
        assertTrue(joinBoardDao.count()==0);
    }

    @Test
    void increaseViewCnt() {

    }
}