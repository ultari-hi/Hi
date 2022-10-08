package com.hi.controller;

import com.hi.domain.CommentReplyDto;
import com.hi.service.CommentReplyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
//
@CrossOrigin(origins = "*") // CORS 에러 처리 용도
@RestController
@RequestMapping("/api/reply")
public class CommentReplyController {
    @Autowired
    CommentReplyServiceImpl commentReplyServiceImpl;

    @GetMapping()   // 전체 대댓글 갯수
    public int count() {
        int count = commentReplyServiceImpl.count();

        return count;
    }

    @GetMapping("/{reply_id}")   // 대댓글 조회
    public ResponseEntity select(@PathVariable Integer reply_id) {
        Map mapArr = new HashMap();
        try{
            CommentReplyDto commentReplyDto = commentReplyServiceImpl.select(reply_id);
            mapArr.put("reply",commentReplyDto);

            return new ResponseEntity<>(mapArr, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/list")      // 대댓글 목록 조회
    public ResponseEntity selectList(@RequestParam Integer comment_id) {
        Map mapArr = new HashMap();
        try{
            List<CommentReplyDto> list = commentReplyServiceImpl.selectList(comment_id);
            mapArr.put("reply",list);

            return new ResponseEntity<>(mapArr, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/")         // 대댓글 생성
    public ResponseEntity write(@RequestBody CommentReplyDto dto) {
        try{
            int result = commentReplyServiceImpl.write(dto);

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/")         // 대댓글 수정
    public ResponseEntity modify(@RequestBody CommentReplyDto dto) {
        try{
            int result = commentReplyServiceImpl.modify(dto);

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/")         // 대댓글 삭제
    public ResponseEntity remove(@RequestBody CommentReplyDto dto) {
        try{
            int result = commentReplyServiceImpl.remove(dto.getReply_id());

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
