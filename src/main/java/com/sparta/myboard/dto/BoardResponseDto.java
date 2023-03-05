package com.sparta.myboard.dto;

import com.sparta.myboard.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {
    private final Long id;
    private final String title;
    private final String contents;
    private final String username;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.username = board.getUsername();
        this.createAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }
}
