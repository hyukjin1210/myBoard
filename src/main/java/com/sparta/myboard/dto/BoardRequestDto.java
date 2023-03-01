package com.sparta.myboard.dto;

import lombok.Getter;

@Getter
public class BoardRequestDto {
    private String title;
    private String writer;
    private String password;
    private String contents;

}
