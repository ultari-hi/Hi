package com.hi.crawling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CrawlingService {
    @Autowired
    CrawlingDao crawlingDao;

    public void saveAcm(CrawAcmDto crawAcmDto) {
        crawlingDao.insertAcm(crawAcmDto);
    }

    public void saveAcmImage(Map map) {
        crawlingDao.insertImage(map);
    }

}
