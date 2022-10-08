package com.hi.dao;

import com.hi.dto.TestDto;

import java.util.List;

//@Repository
//@Mapper
public interface TestDao {
    List<TestDto> getAllDataList();
}