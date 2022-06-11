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
    CrawlingAcm crawArm;
    @Autowired
    CrawlingRoom crawRoom;
    @Autowired
    CrawlingAcmList crawList;
    @Autowired
    CrawlingService crawlingService;

    @GetMapping("/crawling")
    public ResponseEntity oneAcm() {

        try {
            /*
                001002001 : 파리
                005001002 : LA
                001001001 : 런던
                001003001 : 로마
                005001001 : 뉴욕
                001003003 : 피렌체
                001011001 : 프라하
                001004001 : 바르셀로나
                001012001 : 부다페스트
                001004002 : 마드리드
            */
            String[] cityArr={"005001002","001001001","001003001","005001001","001003003","001011001","001004001","001012001","001004002",""};
            List<CrawAcmDto> acmDtoList = new ArrayList<>();
            Map map = new HashMap();

            for(String cityCode : cityArr){
    //            Map map = new HashMap();
//                String cityCode ="001002001";
                String admListUrl = "https://www.theminda.com/md/stays?stayType=homestay&cityCode="+cityCode;
    //            https://www.theminda.com/md/stays?stayType=hostels&cityCode=001002001         // 호스텔
    //            https://www.theminda.com/md/stays?stayType=hotels&cityCode=001002001          // 호텔
    //            https://www.theminda.com/md/stays?partner=agoda&stayType=hotels&cityCode=001002001&cityName=%ED%8C%8C%EB%A6%AC

                List<String> admIdList = crawList.crawAcmList(admListUrl);  // 숙소 코드 목록을 리스트로 받아옴

    //            List<CrawAcmDto> acmDtoList = new ArrayList<>();
                for (String admId : admIdList){     // admId : 숙소 코드
                    String url = "https://www.theminda.com/main/view.php?goodsno="+admId;

                    CrawAcmDto crawlingAcm = crawArm.crawAcm(admId, url);  // 숙소 상세 정보 크롤링
                    crawlingAcm.setRooms(crawRoom.crawRoom(url));   // 숙소 상세 정보 중 객실 정보 크롤링

                    acmDtoList.add(crawlingAcm);    // 크롤링 완료 후 객체 리스트에 저장
                    System.out.println("====== "+admId+" Saving.... ========");
                    System.out.println(crawlingAcm);
                    crawlingService.saveAcm(crawlingAcm);// DB에 크롤링한 숙소 데이터 저장

                    Map imageMap = new HashMap();
                    // 이미지 테이블에 필요한 정보만 추출
                    imageMap.put("accommodation_id",crawlingAcm.getAccommodation_id());

                    String[] ImageArr = crawlingAcm.getImage().split(",");
                    for(String image : ImageArr){
                        imageMap.put("image",image);
                        crawlingService.saveAcmImage(imageMap);        // DB에 크롤링한 데이터 저장
                    }
                    System.out.println("====== "+admId+" Save Complete ========");
                }

            }


//            // DB에 크롤링한 숙소 이미지 데이터 저장
//            Map imageMap = new HashMap();
//            for(CrawAcmDto crawAcmDto : acmDtoList){
//                // 이미지 테이블에 필요한 정보만 추출
//                imageMap.put("accommodation_id",crawAcmDto.getAccommodation_id());
//
//                String[] ImageArr = crawAcmDto.getImage().split(",");
//                for(String image : ImageArr){
//                    imageMap.put("image",image);
//                    crawlingService.saveAcmImage(imageMap);        // DB에 크롤링한 데이터 저장
//                }
//            }

                map.put("Acm",acmDtoList);                  // 브라우저에 크롤링 결과 출력
                map.put("totalCount",acmDtoList.size());

            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

}
