package com.hi.dao;

import com.hi.dto.TestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestMapperImplTest {
    @Autowired
    TestDao testDao;

    @Test
    void getAllDataList() {
        List<TestDto> list = testDao.getAllDataList();
        assertTrue(list.size()==3);
    }
}