package com.hi.comtroller;

import com.hi.domain.JoinBoardDto;
import com.hi.domain.PageHandler;
import com.hi.domain.SearchCondition;
import com.hi.service.JoinBoardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    @GetMapping("/join/{board_id}")
    public ResponseEntity<JoinBoardDto> read(@PathVariable Integer board_id) {         // 동행자 게시글 상세 조회
        try {
            JoinBoardDto joinBoardDto = joinBoardService.read(board_id);

            return new ResponseEntity<JoinBoardDto>(joinBoardDto, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/join/search")
    public ResponseEntity search(
//            @RequestParam(required = false, defaultValue = "1") int type,
            @RequestParam(required = false, defaultValue = "") String region,
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "") String go_with_start,
            @RequestParam(required = false, defaultValue = "") String go_with_end){
        /*
            type : 1 -> 제목으로 검색
            type : 2 -> 제목 + 본문 검색

            검색 조건 : 도시, 날짜, 제목
        */

        try {
            List<JoinBoardDto> list = joinBoardService.search(region, keyword, go_with_start, go_with_end);

            return new ResponseEntity<>(list, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/join/list")
    public ResponseEntity readAll(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "20") int pageSize
    ) {         // 동행자 게시글 목록 조회

        SearchCondition searchCondition = new SearchCondition(page,pageSize,"","");
        PageHandler pageHandler = new PageHandler(joinBoardService.count(),searchCondition);

        if(pageHandler.getTotalPage()<page) page=pageHandler.getTotalPage();

        Map mapArr = new HashMap();
        try{
            List<JoinBoardDto> list = joinBoardService.selectPage(searchCondition);
            pageHandler.setBoardCurCount(list.size());

            mapArr.put("meta",pageHandler);
            mapArr.put("boards",list);

            return new ResponseEntity<>(mapArr,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(mapArr, HttpStatus.BAD_REQUEST);    //상태코드 400
        }
    }

    @PostMapping("/join")
    public ResponseEntity write(@RequestBody JoinBoardDto dto, HttpSession session) {         // 동행자 게시글 생성
        if(!loginCheck(session)){
            resultSet(0);   // error : true 후 반환
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }

        try{
            int result = joinBoardService.write(dto);   // 생성한 갯수(1)를 반환
            System.out.println("================== "+result+" =====================");

            resultSet(result);
            return new ResponseEntity<>(map,HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/join")
    public ResponseEntity modify(@RequestBody JoinBoardDto dto, HttpSession session) {         // 동행자 게시글 수정
        // 게시글 정보와 작성자 정보가 일치해야 수정 진행
        if(!writerCheck(dto.getBoard_id(), session)){
            resultSet(0);   // error : true 후 반환
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }
        System.out.println(dto);

        try{
            int result = joinBoardService.modify(dto);
            System.out.println("================== "+result+" =====================");

            resultSet(result);
            // 수정 성공 시 수정된 게시글 화면을 보여주기 위해 joinBoardDto 전달 -> 프론트에서 해당 기존 페이지에서 변경된 정보로 출력
            JoinBoardDto joinBoardDto = joinBoardService.read(dto.getBoard_id());
            map.put("data",joinBoardDto);

            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/join")
    public ResponseEntity remove(@RequestBody JoinBoardDto dto, HttpSession session) {         // 동행자 게시글 삭제
        // 게시글 정보와 작성자 정보가 일치해야 삭제 진행
        if(!writerCheck(dto.getBoard_id(), session))
            return new ResponseEntity<>("ERROR",HttpStatus.BAD_REQUEST);

        try{
            int result = joinBoardService.remove(dto.getBoard_id());
            System.out.println("================== "+result+" =====================");

            resultSet(result);

            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
