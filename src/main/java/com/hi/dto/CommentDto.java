package com.hi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor // 자동으로 모든 매개변수를 받는 생성자를 생성
@JsonInclude(JsonInclude.Include.NON_NULL)  // 응답 시 null인 값을 자동으로 제거
public class CommentDto {
    private Integer comment_id;
    private Integer board_id;
    private String content;
    private String created_at;
    private String updated_at;
    private String nickname;
    private List<CommentReplyDto> reply;
}
