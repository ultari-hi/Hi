package com.hi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor // 자동으로 모든 매개변수를 받는 생성자를 생성
public class JoinBoardDto {
    private Integer board_id;
    private String title;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private String region;
    private LocalDate go_with_start;
    private LocalDate go_with_end;
    private String content;
    private String nickname;
    private String gender;
    private Integer together;

    public JoinBoardDto(String title, String region, LocalDate go_with_start, LocalDate go_with_end, String content, String nickname, Integer together) {
        this.title = title;
        this.region = region;
        this.go_with_start = go_with_start;
        this.go_with_end = go_with_end;
        this.content = content;
        this.nickname = nickname;
        this.together = together;
    }

    @Override
    public String toString() {
        return "JoinBoardDto{" +
                "board_id=" + board_id +
                ", title='" + title + '\'' +
                ", create_at=" + created_at +
                ", update_at=" + updated_at +
                ", region='" + region + '\'' +
                ", go_with_start=" + go_with_start +
                ", go_with_end=" + go_with_end +
                ", content='" + content + '\'' +
                ", nickname='" + nickname + '\'' +
                ", gender='" + gender + '\'' +
                ", together='" + together + '\'' +
                '}';
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
