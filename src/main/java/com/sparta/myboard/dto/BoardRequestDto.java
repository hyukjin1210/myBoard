package com.sparta.myboard.dto;

import lombok.Getter;

@Getter
public class BoardRequestDto {
    private String title;
    private String writer;
    private String password;
    private String contents;


//    public BoardRequestDto(String title, String writer, String password, String contents) {
//        this.title = title;
//        this.writer = writer;
//        this.password = password;
//        this.contents = contents;
//    }
}
