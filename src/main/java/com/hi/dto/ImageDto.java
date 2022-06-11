package com.hi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ImageDto {
    List<String> urlList;
}
