package com.hi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor // 자동으로 모든 매개변수를 받는 생성자를 생성
@JsonInclude(JsonInclude.Include.NON_NULL)  // 응답 시 null인 값을 자동으로 제거
public class JoinBoardDto {
    private Integer board_id;
    private String title;
    private String created_at;
    private String updated_at;
    private String region;
    private String go_with_start;
    private String go_with_end;
    private String content;
    private String nickname;
    private String gender;
    private Integer together;

    public JoinBoardDto(String title, String region, String go_with_start, String go_with_end, String content, String nickname, Integer together) {
        this.title = title;
        this.region = region;
        this.go_with_start = go_with_start;
        this.go_with_end = go_with_end;
        this.content = content;
        this.nickname = nickname;
        this.together = together;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JoinBoardDto that = (JoinBoardDto) o;
        return board_id.equals(that.board_id) && title.equals(that.title) && created_at.equals(that.created_at) && updated_at.equals(that.updated_at) && region.equals(that.region) && go_with_start.equals(that.go_with_start) && go_with_end.equals(that.go_with_end) && content.equals(that.content) && nickname.equals(that.nickname) && gender.equals(that.gender) && together.equals(that.together);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board_id, title, created_at, updated_at, region, go_with_start, go_with_end, content, nickname, gender, together);
    }
}
