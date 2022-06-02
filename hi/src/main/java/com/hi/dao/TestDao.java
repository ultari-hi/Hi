package com.hi.dao;

import com.hi.domain.TestDto;

import java.util.List;

//@Repository
//@Mapper
public interface TestDao {
    List<TestDto> getAllDataList();
}