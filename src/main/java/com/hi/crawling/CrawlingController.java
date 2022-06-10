package com.hi.crawling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CrawlingController {
    @Autowired
    CrawlingAcm ca;
    @Autowired
    CrawlingRoom cr;
    @Autowired
    CrawlingAcmList cal;

    @GetMapping("/crawling")
    public ResponseEntity oneAcm() {

        try {
            Map map = new HashMap();
            String cityCode ="001002001";
            String admListUrl = "https://www.theminda.com/md/stays?stayType=homestay&cityCode="+cityCode;
//            https://www.theminda.com/md/stays?stayType=hostels&cityCode=001002001         // 호스텔
//            https://www.theminda.com/md/stays?stayType=hotels&cityCode=001002001          // 호텔
//            https://www.theminda.com/md/stays?partner=agoda&stayType=hotels&cityCode=001002001&cityName=%ED%8C%8C%EB%A6%AC

            List<String> admIdList = cal.crawAcmList(admListUrl);

            List<CrawAcmDto> acmDtoList = new ArrayList<>();
            for (String admId : admIdList){
                String url = "https://www.theminda.com/main/view.php?goodsno="+admId;

                CrawAcmDto crawlingAcm = ca.crawAcm(url);
                crawlingAcm.setRooms(cr.crawRoom(url));

                acmDtoList.add(crawlingAcm);
            }
                map.put("Acm",acmDtoList);
                map.put("totalCount",acmDtoList.size());

            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

}
