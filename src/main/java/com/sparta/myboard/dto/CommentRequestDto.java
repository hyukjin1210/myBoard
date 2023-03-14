package com.sparta.myboard.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private long boardId;
    private String content;
}
