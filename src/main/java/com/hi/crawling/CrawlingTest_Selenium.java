package com.hi.crawling;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CrawlingTest_Selenium {
    @GetMapping("/crawling2")
    public String crawling()  {
        final String WEB_DRIVER_ID = "webdriver.chrome.driver"; //드라이버 ID
        final String WEB_DRIVER_PATH = "src/main/resources/selenium/chromedriver"; //드라이버 경로

        //드라이버 설정
        try {
                System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        ChromeOptions options = new ChromeOptions(); // 크롬 설정을 담은 객체 생성
        options.addArguments("headless"); // 브라우저 창을 띄우지 않는 옵션
        WebDriver driver = new ChromeDriver(options);

        //이동을 원하는 url
        String url = "https://www.theminda.com/main/view.php?goodsno=15690";
        driver.get(url); // get()을 통해 WebDriver을 해당 url로 이동한다.

        try {Thread.sleep(2000);} catch (InterruptedException e) {}

        try{
            // 실제 코드 작성


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
        return "Crawling Success";
    }
}


