package com.hi.crawling;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class CrawlingAcmList {  // 객실 목록 크롤링 -> 객실 정보 수집
    public List<String> crawAcmList(String url)  {
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
        driver.get(url); // get()을 통해 WebDriver을 해당 url로 이동한다.

        WebElement scroll = driver.findElement(By.className("info_area"));

        // 스크롤 지속시간 설정
        var stTime = new Date().getTime();
        try {
            while (new Date().getTime() < stTime + 30000) { //30초 동안 무한스크롤 지속
                Thread.sleep(500); //리소스 초과 방지
                // executeScript: 해당 페이지에 JavaScript 명령을 보낸다.
                ((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)", scroll);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("scroll : " + scroll);

        //브라우저 이동시 생기는 로드시간을 기다린다.
        //HTTP응답속도보다 자바의 컴파일 속도가 더 빠르기 때문에 임의적으로 1초를 대기한다.
        try {Thread.sleep(2000);} catch (InterruptedException e) {}

        List<String> admIdList = new ArrayList<>(); // 숙소 코드들을 저장 후 결과를 반환할 리스트
        try{
            List<WebElement> admId = driver.findElements(By.cssSelector("#ulStays > li > a"));  // 웹 엘리먼트 객체(숙소 코드 정보)를 리스트에 저장
            for(WebElement element : admId){
                admIdList.add(element.getAttribute("data-goodsno"));    // 반복문 및 속성 값 추출을 통해 숙소 코드 저장
            }

        } catch (Exception e){
            e.printStackTrace();
        }

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
        return admIdList;
    }
}


