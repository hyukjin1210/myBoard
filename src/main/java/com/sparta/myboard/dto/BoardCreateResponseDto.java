package com.sparta.myboard.dto;

import com.sparta.myboard.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardCreateResponseDto {
    private String writer;
    private String title;
    private String contents;
    private String password;

    private LocalDateTime createAt;

    public BoardCreateResponseDto(Board board) {  //이유: 계층간 간섭을 막기 위해서.
        this.writer = board.getWriter();
        this.title = board.getTitle();
//        this.password = board.getPassword();
//        this.createAt = board.getCreateAt();
        this.contents = board.getContents();
    }
}
