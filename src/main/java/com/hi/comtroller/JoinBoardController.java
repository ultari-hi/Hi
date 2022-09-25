package com.hi.comtroller;

import com.hi.domain.JoinBoardDto;
import com.hi.domain.PageHandler;
import com.hi.domain.SearchCondition;
import com.hi.service.JoinBoardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*") // CORS 에러 처리 용도
@RestController
@RequestMapping("/api/board")
public class JoinBoardController {
    @Autowired
    JoinBoardServiceImpl joinBoardService;
    String success="true";
    String error=null;
    JoinBoardDto data=null;
    Map map=new HashMap();

    @GetMapping("/join/{board_id}")        // 동행자 게시글 상세 조회
    public ResponseEntity<JoinBoardDto> read(@PathVariable Integer board_id) {
        try {
            JoinBoardDto joinBoardDto = joinBoardService.read(board_id);

            return new ResponseEntity<JoinBoardDto>(joinBoardDto, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/join/list")         // 동행자 게시글 목록 조회
    public ResponseEntity readAll(
            // 페이징 처리는 프론트에서 더보기로 처리하기에 page,pageSize 히든으로 받기
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        // 페이징 처리
        SearchCondition searchCondition = new SearchCondition(page,pageSize,"","");
        PageHandler pageHandler = new PageHandler(joinBoardService.count(),searchCondition);

        if(pageHandler.getTotalPage() < page) // page 값이 TotalPage값보다 큰 값이 입력될 경우 page값을 총 페이지 값으로 변경 (에러 방지)
            {
                page=pageHandler.getTotalPage();
                searchCondition = new SearchCondition(page,pageSize,"","");
                pageHandler = new PageHandler(joinBoardService.count(),searchCondition);
            }

        Map mapArr = new HashMap();
        try{
            List<JoinBoardDto> list = joinBoardService.selectPage(searchCondition);
            pageHandler.setBoardCurCount(list.size());  // 현재 출력될 게시글 갯수

            mapArr.put("meta",pageHandler);
            mapArr.put("boards",list);

            return new ResponseEntity<>(mapArr,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(mapArr, HttpStatus.BAD_REQUEST);    //상태코드 400
        }
    }

    @GetMapping("/join/search")
    public ResponseEntity search(
            // 검색 조건 : 도시, 게시글 제목, 날짜
            @RequestParam(required = false, defaultValue = "") String region,   // 도시
            @RequestParam(required = false, defaultValue = "") String title,  // 게시글 제목
            @RequestParam(required = false, defaultValue = "2000-01-01") String go_with_start,
            @RequestParam(required = false, defaultValue = "9999-12-30") String go_with_end,
            // 페이징 처리는 프론트에서 더보기로 처리하기에 page,pageSize 히든으로 받기
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize){

        // 페이징 처리
        SearchCondition searchCondition = new SearchCondition(page,pageSize,"","");
        PageHandler pageHandler = new PageHandler(joinBoardService.searchCount(region,title,go_with_start,go_with_end),searchCondition);


        System.out.println("=========== "+joinBoardService.searchCount(region,title,go_with_start,go_with_end));

        if(pageHandler.getTotalPage() < page) // page 값이 TotalPage값보다 큰 값이 입력될 경우 page값을 총 페이지 값으로 변경 (에러 방지)
            {
                page=pageHandler.getTotalPage();
                searchCondition = new SearchCondition(page,pageSize,"","");
                pageHandler = new PageHandler(joinBoardService.searchCount(region,title,go_with_start,go_with_end),searchCondition);
            }

        Map mapArr = new HashMap();
        try {
            List<JoinBoardDto> list = joinBoardService.search(region, title, go_with_start, go_with_end, searchCondition);
            pageHandler.setBoardCurCount(list.size());  // 현재 출력될 게시글 갯수

            mapArr.put("meta",pageHandler);
            mapArr.put("boards",list);

            return new ResponseEntity<>(mapArr, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            resultSet(0);
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/join")         // 동행자 게시글 생성
    public ResponseEntity write(@RequestBody JoinBoardDto dto, HttpSession session) {
        /*
        if(!loginCheck(session)){
            resultSet(0);   // error : true 후 반환
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }
        */

        try{
            int result = joinBoardService.write(dto);   // 생성한 갯수(1)를 반환
            System.out.println("================== "+result+" =====================");

            resultSet(result);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(new URI("/board/join/list"));   // 리다이렉션 경로 입력

            return new ResponseEntity<>(httpHeaders,HttpStatus.MOVED_PERMANENTLY);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/join")         // 동행자 게시글 수정
    public ResponseEntity modify(@RequestBody JoinBoardDto dto, HttpSession session) {
        /*
        // 게시글 정보와 작성자 정보가 일치해야 수정 진행
        if(!writerCheck(dto.getBoard_id(), session)){
            resultSet(0);   // error : true 후 반환
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }
         */

        try{
            int result = joinBoardService.modify(dto);
            System.out.println("================== "+result+" =====================");
            resultSet(result);

            // 수정 성공 시 수정된 게시글 화면을 보여주기 위해 joinBoardDto 전달 -> 프론트에서 해당 기존 페이지에서 변경된 정보로 출력
            JoinBoardDto joinBoardDto = joinBoardService.read(dto.getBoard_id());

            return new ResponseEntity<JoinBoardDto>(joinBoardDto,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/join")         // 동행자 게시글 삭제
    public ResponseEntity remove(@RequestBody JoinBoardDto dto, HttpSession session) {
        // 게시글 정보와 작성자 정보가 일치해야 삭제 진행
        if(!writerCheck(dto.getBoard_id(), session)){
            resultSet(0);   // error : true 후 반환
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }

        try{
            int result = joinBoardService.remove(dto.getBoard_id());
            System.out.println("================== "+result+" =====================");
            resultSet(result);

            // 삭제 실패 시 숙소 목록으로 리다이렉션
            if(result==0){
                return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
            }

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(new URI("/board/join/list"));   // 리다이렉션 경로 입력

            return new ResponseEntity<>(httpHeaders,HttpStatus.MOVED_PERMANENTLY);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }
    }

    private boolean loginCheck(HttpSession session) {
        // 세션에 id가 있는지 확인, 있으면 true를 반환
//        return session.getAttribute("id")!=null;
        return true;
    }

    private boolean writerCheck(Integer board_id, HttpSession session){
        String nickname = (String) session.getAttribute("id");
//        return joinBoardService.writerCheck(board_id, nickname)!=null? true : false;
        return true;
    }

    private void resultSet(int result) {
        if(result==1){
            success="true";
            data=null;  // 이전 데이터 값 초기화
            error=null;
        } else {
            success=null;
            data=null;  // 이전 데이터 값 초기화
            error="true";
        }
        map.put("success",success);
        map.put("data",data);
        map.put("error",error);
    }
}
