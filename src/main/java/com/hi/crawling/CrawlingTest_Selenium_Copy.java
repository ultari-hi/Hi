//package com.hi.crawling;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.Date;
//import java.util.List;
//
//@Controller
//public class CrawlingTest_Selenium_Copy {
//    @GetMapping("/crawling")
//    public String crawling()  {
//        final String WEB_DRIVER_ID = "webdriver.chrome.driver"; //드라이버 ID
//        final String WEB_DRIVER_PATH = "src/main/resources/selenium/chromedriver"; //드라이버 경로
//
//
//        //드라이버 설정
//        try {
//                System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        /*
//            코드 실행 시 브라우저가 눈에 보이지 않고 내부적으로 실행된다.
//            options.addArguments("headless") -> 생략 시 실제 크롬 창이 생성되고, 어떤 순서로 진행되는지 확인 할 수 있다.
//        */
//        ChromeOptions options = new ChromeOptions(); // 크롬 설정을 담은 객체 생성
//        options.addArguments("headless"); // 브라우저 창을 띄우지 않는 옵션
//
//        /*
//            위에서 설정한 옵션을 담을 드라이버 객체 생성
//            옵션을 설정하지 않았을 때 new ChromeDriver(options)의 option은 생략이 가능하다.
//            WebDriver객체가 곧 하나의 브라우저 창이라 생각하면 된다.
//        */
//        WebDriver driver = new ChromeDriver(options);
//
//        //이동을 원하는 url
//        String url = "https://domestic-order-site.yanolja.com/review/properties/3006234/reviews";
//        driver.get(url); // get()을 통해 WebDriver을 해당 url로 이동한다.
//
//        //브라우저 이동시 생기는 로드시간을 기다린다.
//        //HTTP응답속도보다 자바의 컴파일 속도가 더 빠르기 때문에 임의적으로 1초를 대기한다.
//        try {Thread.sleep(1000);} catch (InterruptedException e) {}
//
//        /*
//             WebElement : html의 태그를 가지는 클래스
//             class="item-container" 인 클래스를 가진 WebElement를 받아온다.
//             scroll : 크롤링 하려는 리뷰 페이지가 스크롤을 내릴 수록 추가 리뷰가 출력되는 형식으로 되어있다.
//                     :  해당 페이지 스크롤을 내리기 위해 리뷰전체 div를 지정
//        */
//        WebElement scroll = driver.findElement(By.className("item-container"));
//
//        // 스크롤 지속시간 설정
//        var stTime = new Date().getTime();
//        try {
//            while (new Date().getTime() < stTime + 60000) { //60초 동안 무한스크롤 지속
//                Thread.sleep(500); //리소스 초과 방지
//                // executeScript: 해당 페이지에 JavaScript 명령을 보낸다.
//                ((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)", scroll);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("scroll : " + scroll);
//
//        // 파싱 할 각 엘리먼트를 적용
//        List<WebElement> user = driver.findElements(By.cssSelector("div.profile"));     // 작성자 정보
//        List<WebElement> reviews = driver.findElements(By.cssSelector("p.content"));    // 리뷰 모음
//        List<WebElement> moreReviews = driver.findElements(By.cssSelector("p > a.more-btn"));   // 더보기 버튼
//
//        // 리뷰 중 더보기 버튼이 있는 경우 클릭하여 전체 내용이 출력되도록 한다.
//        for(int i=0; i < moreReviews.size(); i++){
//            if(moreReviews.get(i).getText().equals("더보기"))
//                moreReviews.get(i).sendKeys("\n");
//        }
//
//        // 크롤링한 객체를 콘솔창에 출력
//        for (int i=0; i < reviews.size();i++){
//            String id = user.get(i).getText().split("\\|")[0];
//            String review = reviews.get(i).getText();
//
//            System.out.println("["+ i + "] : ");
//            System.out.println(id);
//            System.out.println(review);
//            System.out.println("===============================");
//        }
//
//        //1초 대기
//        try {Thread.sleep(1000);} catch (InterruptedException e) {}
//
//        try {
//            //드라이버가 null이 아니라면
//            if(driver != null) {
//                //드라이버 연결 종료
//                driver.close(); //드라이버 연결 해제
//
//                //프로세스 종료
//                driver.quit();
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
//        }
//        return "Crawling Success";
//    }
//}
//
//
