package com.hi.crawling;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CrawlingAcm {
    public CrawAcmDto crawAcm(String url)  {
        final String WEB_DRIVER_ID = "webdriver.chrome.driver"; //드라이버 ID
        final String WEB_DRIVER_PATH = "src/main/resources/selenium/chromedriver"; //드라이버 경로


        //드라이버 설정
        try {
                System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
            } catch (Exception e) {
                e.printStackTrace();
            }

        /*
            코드 실행 시 브라우저가 눈에 보이지 않고 내부적으로 실행된다.
            options.addArguments("headless") -> 생략 시 실제 크롬 창이 생성되고, 어떤 순서로 진행되는지 확인 할 수 있다.
        */
        ChromeOptions options = new ChromeOptions(); // 크롬 설정을 담은 객체 생성
        options.addArguments("headless"); // 브라우저 창을 띄우지 않는 옵션

        /*
            위에서 설정한 옵션을 담을 드라이버 객체 생성
            옵션을 설정하지 않았을 때 new ChromeDriver(options)의 option은 생략이 가능하다.
            WebDriver객체가 곧 하나의 브라우저 창이라 생각하면 된다.
        */
        WebDriver driver = new ChromeDriver(options);

        //이동을 원하는 url
//        String url = "https://www.theminda.com/main/view.php?goodsno=15690";
        driver.get(url); // get()을 통해 WebDriver을 해당 url로 이동한다.

        //브라우저 이동시 생기는 로드시간을 기다린다.
        //HTTP응답속도보다 자바의 컴파일 속도가 더 빠르기 때문에 임의적으로 1초를 대기한다.
        try {Thread.sleep(2000);} catch (InterruptedException e) {}

        CrawAcmDto crawlingAcm = new CrawAcmDto();
        try{
            // 파싱 할 각 엘리먼트를 적용
            crawlingAcm.setName(driver.findElement(By.tagName("h1")).getText());    // 숙소 이름
            try {
                crawlingAcm.setType(driver.findElement(By.cssSelector("#roomTopNm")).getText());    // 객실 타입
            } catch (Exception e) {
                crawlingAcm.setType("<-- Not Found -->");
            }

            try {
                crawlingAcm.setPriceKor(driver.findElement(By.cssSelector("#roomTopPrice_kor")).getText());// 원화 가격
                crawlingAcm.setPrice(driver.findElement(By.cssSelector("#roomTopPrice")).getText());// 현지 가격 (유로, 달러 등)
            } catch (Exception e) {
                crawlingAcm.setPriceKor("<-- Not Found -->");
                crawlingAcm.setPrice("<-- Not Found -->");
            }

            try {
                crawlingAcm.setRating(driver.findElement(By.cssSelector("div.top_info__rating_box > span.top_info__rating--review > span")).getText()); //  리뷰 점수
            } catch (Exception e) {
                crawlingAcm.setRating("<-- Not Found -->");
            }

            try {
                crawlingAcm.setNum_of_review(driver.findElement(By.cssSelector(" div.top_info__rating_box > span.top_info__rating--review > a")).getText());   // 리뷰 갯수
            } catch (Exception e) {
                crawlingAcm.setNum_of_review("<-- Not Found -->");
            }

            List<WebElement> imageList = driver.findElements(By.cssSelector("div.bx-viewport > ul > li > img"));    // 숙소 이미지 목록
            List<String>image=new ArrayList<>();
            for(WebElement element : imageList){
                image.add(element.getAttribute("src"));
            }
            crawlingAcm.setImage(image);     // 숙소 사진

            try {
                crawlingAcm.setIntroduction(driver.findElement(By.cssSelector("#view1 > div > div.column_left > div > p")).getText());    // 숙소 소개글
            } catch (Exception e) {
                crawlingAcm.setIntroduction("<-- Not Found -->");
            }

            // 부가 시설 및 서비스
            List<WebElement> publicFacility = driver.findElements(By.cssSelector("#view6 > div:nth-child(1) > section.introduce1 > div > ul > li > ul > li"));
            List<WebElement> publicFacilityDis = driver.findElements(By.cssSelector(".disabled"));  // 미사용 목록

            String disStr="";
            for (WebElement element : publicFacilityDis){   // 미사용 목록 하나의 문자열로 변환
                disStr+=element.getText()+", ";
            }

//            System.out.println("Disable List : "+disStr);
//            System.out.println("===========================================");

            List<String> publicFacilityList=new ArrayList<>();
            for (int i=0;i<publicFacility.size();i++){
                if(disStr.contains(publicFacility.get(i).getText()))   // 미사용 목록에 포함되면 false
                    publicFacilityList.add(publicFacility.get(i).getText()+":false");
                else
                    publicFacilityList.add(publicFacility.get(i).getText()+":true");
                crawlingAcm.setPublicFacility(publicFacilityList);
           } // 부가 시설 및 서비스 끝

            List<WebElement> mealList = driver.findElements(By.cssSelector("div.col-xs-6.price_info > dl > dd"));
            crawlingAcm.setBreakfast(mealList.get(0).getText());    // 조식
            crawlingAcm.setLunch(mealList.get(0).getText());        // 중식
            crawlingAcm.setDinner(mealList.get(0).getText());       // 석식
            try {
                crawlingAcm.setSpot(driver.findElement(By.cssSelector("section.introduce3.col-xs-6 > ul > p")).getText());  // 숙소 근처 스팟
            } catch (Exception e) {
                crawlingAcm.setSpot("<-- Not Found -->");
            }
            crawlingAcm.setRole(driver.findElement(By.cssSelector("section.introduce6.col-xs-6 > ul > p")).getText());  // 숙소 이용 규칙
            try {
                crawlingAcm.setPaidService(driver.findElement(By.cssSelector("section.introduce4.col-xs-6 > ul > p")).getText());   // 유료 서비스
            } catch (Exception e) {
                crawlingAcm.setPaidService("<-- Not Found -->");
            }
            try{
                crawlingAcm.setPeculiarity(driver.findElement(By.cssSelector("section.introduce5.col-xs-6 > ul > p")).getText());   // 기타 특이사항
            } catch (Exception e){
                crawlingAcm.setPeculiarity("<-- Not Found -->");
            }
            crawlingAcm.setLocation(driver.findElement(By.cssSelector("#view3 > section.address1 > ul")).getText());        // 위치
            crawlingAcm.setHowCome(driver.findElement(By.cssSelector("#view3 > section.address3 > div > ul")).getText());         // 오시는 방법

            crawlingAcm.setCancel(driver.findElement(By.cssSelector("#view3 > table > tbody")).getText());  // 취소환불규정
            crawlingAcm.setCancel(crawlingAcm.getCancel().replace("취소","취소 시"));
            crawlingAcm.setCancel(crawlingAcm.getCancel().replace("0%","0% 환불,"));
            crawlingAcm.setCancel(crawlingAcm.getCancel().replace("8%","없음"));
            crawlingAcm.setCancel(crawlingAcm.getCancel().replace("없음","포인트 적립 없음"));

        } catch (Exception e){
            e.printStackTrace();
        }
//        System.out.println(crawlingAcm);

        //1초 대기
        try {Thread.sleep(1000);} catch (InterruptedException e) {}

        try {
            //드라이버가 null이 아니라면
            if(driver != null) {
                //드라이버 연결 종료
                driver.close(); //드라이버 연결 해제

                //프로세스 종료
                driver.quit();
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return crawlingAcm;
    }
}


