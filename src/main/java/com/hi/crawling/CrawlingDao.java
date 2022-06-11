package com.hi.crawling;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class CrawlingDao {
    @Autowired
    private SqlSession session;
    private static String namespace = "com.hi.dao.CrawlingMapper.";

    // 숙소 데이터 생성
    public void insertAcm(CrawAcmDto crawAcmDto) {
        try{
            System.out.println(crawAcmDto);
            session.insert(namespace+"insertAcm" ,crawAcmDto);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 숙소 이미지데이터 생성
    public void insertImage(Map map) {         // 숙소 이미지데이터 생성
        try{
            session.insert(namespace+"insertImage" ,map);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
