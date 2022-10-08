package com.hi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor // 자동으로 모든 매개변수를 받는 생성자를 생성
public class TestDto {
    String name;
    int age;
    int index;
}
