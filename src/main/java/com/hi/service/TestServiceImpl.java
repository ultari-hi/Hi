package com.hi.service;

import com.hi.dao.TestDao;
import com.hi.dto.TestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    @Autowired
    TestDao testDao;

    @Override
    public List<TestDto> getAllDataList() {
        return testDao.getAllDataList();
    }
}