package com.hi.dao;

import com.hi.domain.TestDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TestDaoImpl implements TestDao {
    @Autowired
    private SqlSession session;
    private static String namespace = "com.hi.dao.TestMapper.";

    @Override
    public List<TestDto> getAllDataList() {
        return session.selectList(namespace+"selectAll");
    }

}
