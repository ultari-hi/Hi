package com.hi.comtroller;

import com.hi.domain.CommentDto;
import com.hi.domain.JoinBoardDto;
import com.hi.service.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*") // CORS 에러 처리 용도
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    CommentServiceImpl commentServiceImpl;

    @GetMapping()   // 전체 댓글 갯수
    public int count() {
        int count = commentServiceImpl.count();

        return count;
    }

    @GetMapping("/{comment_id}")   // 댓글 조회
    public ResponseEntity select(@PathVariable Integer comment_id) {
        Map mapArr = new HashMap();
        try{
            CommentDto commentDto = commentServiceImpl.select(comment_id);
            mapArr.put("comment",commentDto);

            return new ResponseEntity<>(mapArr, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/list")      // 댓글 목록 조회
    public ResponseEntity selectList(@RequestParam Integer board_id) {
        Map mapArr = new HashMap();
        try{
            List<CommentDto> list = commentServiceImpl.selectList(board_id);
            mapArr.put("comments",list);

            return new ResponseEntity<>(mapArr, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/")         // 댓글 생성
    public ResponseEntity write(@RequestBody CommentDto dto) {
        try{
            int result = commentServiceImpl.write(dto);

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/")         // 댓글 수정
    public ResponseEntity modify(@RequestBody CommentDto dto) {
        try{
            int result = commentServiceImpl.modify(dto);

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/")         // 댓글 삭제
    public ResponseEntity remove(@RequestBody CommentDto dto) {
        try{
            int result = commentServiceImpl.remove(dto.getComment_id());

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
