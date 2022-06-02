package com.hi.comtroller;

import com.hi.domain.JoinBoardDto;
import com.hi.domain.TestDto;
import com.hi.service.JoinBoardServiceImpl;
import com.hi.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            JoinBoardDto joinBoardDto =  joinBoardService.read(board_id);
//            JoinBoardDto joinBoardDto =  new JoinBoardDto();
//            joinBoardDto.setBoard_id(1);
//            joinBoardDto.setUser_id("109");
//            joinBoardDto.setTitle("안녕하세요");
//            joinBoardDto.setRegion("파리");
//            joinBoardDto.setContent("1월 1일 같이 새해 맞이할 분 구해요");
//            joinBoardDto.setNickname("sangWon");
//            joinBoardDto.setGender("남");

            return new ResponseEntity<JoinBoardDto>(joinBoardDto, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/join/list")
    public ResponseEntity readAll() {         // 동행자 게시글 목록 조회
        Map mapArr = new HashMap();

        try{
            List<JoinBoardDto> list = joinBoardService.readAll();
            mapArr.put("success",true);
            mapArr.put("data",list);
            mapArr.put("error",false);

            return new ResponseEntity<>(mapArr,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();

            mapArr.put("success",false);
            mapArr.put("error",true);
            return new ResponseEntity<>(mapArr, HttpStatus.BAD_REQUEST);    //상태코드 400
        }
    }

    @PostMapping("/join/{board_id}")
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

    @PatchMapping("/join/{board_id}")
    public ResponseEntity modify(@RequestBody JoinBoardDto dto, HttpSession session) {         // 동행자 게시글 수정
        // 게시글 정보와 작성자 정보가 일치해야 수정 진행
        if(!writerCheck(dto.getBoard_id(), session)){
            resultSet(0);   // error : true 후 반환
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }

        try{
            int result = joinBoardService.modify(dto);
            System.out.println("================== "+result+" =====================");

            resultSet(result);
            // 수정 성공 시 수정된 게시글 화면을 보여주기 위해 board_id를 전달 -> 프론트에서 해당 board_id로 재호출
            map.put(data,read(dto.getBoard_id()));

            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/join/{board_id}")
    public ResponseEntity remove(@PathVariable Integer board_id, HttpSession session) {         // 동행자 게시글 삭제
        // 게시글 정보와 작성자 정보가 일치해야 삭제 진행
        if(!writerCheck(board_id, session))
            return new ResponseEntity<>("ERROR",HttpStatus.BAD_REQUEST);

        try{
            int result = joinBoardService.remove(board_id);
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
