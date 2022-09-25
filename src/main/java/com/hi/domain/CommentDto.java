package com.hi.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
//@AllArgsConstructor // 자동으로 모든 매개변수를 받는 생성자를 생성
@JsonInclude(JsonInclude.Include.NON_NULL)  // 응답 시 null인 값을 자동으로 제거
public class CommentDto {

}
