//package com.hi.comtroller;
//
//import com.hi.domain.CommentDto;
//import com.hi.service.CommentServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@CrossOrigin(origins = "*") // CORS 에러 처리 용도
//@RestController
//@RequestMapping("/api/comment")
//public class CommentController {
//    @Autowired
//    CommentServiceImpl commentServiceImpl;
//
//    @GetMapping()   // 전체 댓글 갯수
//    public int count() {
//
//    }
//
//    @GetMapping()   // 댓글 조회
//    public ResponseEntity select(Integer comment_id) {
//        try{
//            CommentDto commentDto = commentServiceImpl.select(comment_id);
//
//            return new ResponseEntity<CommentDto>(commentDto, HttpStatus.OK);
//        } catch (Exception e){
//            e.printStackTrace();
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping()      // 댓글 목록 조회
//    public ResponseEntity selectList() {
//        try{
//            List<CommentDto> list = commentServiceImpl.selectList();
//
//            return new ResponseEntity<>(list, HttpStatus.OK);
//        } catch (Exception e){
//            e.printStackTrace();
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PostMapping()         // 댓글 생성
//    public ResponseEntity write(CommentDto dto) {
//        try{
//            int result = commentServiceImpl.write(dto);
//
//            return new ResponseEntity(HttpStatus.OK);
//        } catch (Exception e){
//            e.printStackTrace();
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PatchMapping()         // 댓글 수정
//    public ResponseEntity modify(CommentDto dto) {
//
//    }
//
//    @DeleteMapping()         // 댓글 삭제
//    public ResponseEntity remove(Integer comment_id) {
//
//    }
//}
