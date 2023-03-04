package com.sparta.myboard.dto;

import com.sparta.myboard.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {
    private final String title;
    private final String writer;
    private final String contents;
    private final LocalDateTime createAt;

    public BoardResponseDto(Board board) {
        this.title = board.getTitle();
        this.writer = board.getWriter();
        this.contents = board.getContents();
        this.createAt = board.getCreateAt();
    }
}
