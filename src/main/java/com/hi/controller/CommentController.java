package com.hi.controller;

import com.hi.domain.CommentDto;
import com.hi.service.CommentReplyServiceImpl;
import com.hi.service.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // CORS 에러 처리 용도
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    CommentServiceImpl commentServiceImpl;
    @Autowired
    CommentReplyServiceImpl commentReplyService;

    @GetMapping()   // 전체 댓글 갯수
    public int count() {
        int count = commentServiceImpl.count();

        return count;
    }

    @GetMapping("/{comment_id}")   // 댓글 조회
    public ResponseEntity select(@PathVariable Integer comment_id) {
        try{
            CommentDto commentDto = commentServiceImpl.select(comment_id);  // 댓글 객체 조회
            commentDto.setReply(commentReplyService.selectList(comment_id));    // 세터를 이용해 댓글객체에 대댓글 객체 입력

            return new ResponseEntity<>(commentDto, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/list")      // 댓글 목록 조회
    public ResponseEntity selectList(@RequestParam Integer board_id) {
        try{
            List<CommentDto> comments = commentServiceImpl.selectList(board_id);    // 댓글 객체들 불러옴

            for(int i=0;i<comments.size();i++)
                comments.get(i).setReply(commentReplyService.selectList(comments.get(i).getComment_id())); // 각 댓글에 있는 대댓글 객체를 입력

            return new ResponseEntity<>(comments, HttpStatus.OK);
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
            commentReplyService.removeAll(dto.getComment_id());
            commentServiceImpl.remove(dto.getComment_id());

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
