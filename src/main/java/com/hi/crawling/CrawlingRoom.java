package com.hi.crawling;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Controller
public class CrawlingRoom {
    @GetMapping("/crawRoom")
    public List<CrawRoomDto> crawRoom(String url)  {
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


            List<CrawRoomDto> crawRoomDtoList = new ArrayList<>();  // 객체를 담아서 전송할 CrawRoomDto list 생성
        try{
            // li태그로 되어있는 객실 목록을 리스트로 받아온다. (객체 당 li 태그로 된 객실 하나)
            List<WebElement> list = driver.findElements(By.cssSelector("#roomChk > div > div.room_list__wrap > ul > li"));

            CrawRoomDto crawRoomDto;
            for(WebElement element : list){     // 수집한 엘리먼트 수 만큼 반복문 수행
                crawRoomDto = new CrawRoomDto();

                crawRoomDto.setName(element.findElement(By.cssSelector("div.title__box > h2")).getText());  // 객실 이름
                crawRoomDto.setType(element.findElement(By.cssSelector("div.list__txt--bottom--left > div:nth-child(1) > span:nth-child(1)")).getText());   // 객실 타입
                crawRoomDto.setNumber_of_people(element.findElement(By.cssSelector("div.list__txt--bottom--left > div:nth-child(1) > span:nth-child(2)")).getText());   // 이용 가능 인원
                crawRoomDto.setMinStay(element.findElement(By.cssSelector("div.list__txt--bottom--left > div:nth-child(2) > span")).getText());     // 최소 이용일
                String[] priceArr = element.findElement(By.cssSelector("div.price_box > span.current_price_box")).getText().split("원");
                crawRoomDto.setPriceKor(priceArr[0]);   // 원화 가격
                crawRoomDto.setPrice(priceArr[1]);      // 현지 가격
                crawRoomDto.setPriceBasis(element.findElement(By.cssSelector("div.list__txt--bottom--right > div.price__wrap > div.price_basis_box")).getText());   // 요금 기준 (ex 1인 1박)
                crawRoomDto.setPicture(element.findElement(By.cssSelector("div.list_contents--left > a > div > img")).getAttribute("src")); // 객실 이미지
//                crawRoomDto.setFacilities(element.findElement(By.cssSelector("")).getText());

//                element.findElement(By.cssSelector("#roomChk > div > div.room_list__wrap > ul > li:nth-child(1) > div.list_contents--left > a > div > img")).click();
//                List<WebElement> ll = element.findElements(By.cssSelector("#room_contents > div.detail_pic_wrp.slider_bx.bx_open > div > div.bx-viewport > ul > li > center > img"));
//                    for(WebElement element1 : ll){
//                        crawRoomDto.setPicture(element1.getAttribute("src"));
//                        System.out.println(element1.getAttribute("src"));
//                    }
//                try {Thread.sleep(1000);} catch (InterruptedException e) {}
//                element.findElement(By.cssSelector(".icon_pop_close")).click();
//                ((JavascriptExecutor)driver).executeScript("windows.scroll(0.479)");
//                Action action = new Action(driver);


                crawRoomDtoList.add(crawRoomDto);   // 하나의 객실 크롤링 완료 시 리스트에 저장
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
        return crawRoomDtoList;
    }
}


