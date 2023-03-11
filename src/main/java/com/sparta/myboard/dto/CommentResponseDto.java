package com.sparta.myboard.dto;

import com.sparta.myboard.entity.Board;
import com.sparta.myboard.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private final Long id;
    private final String content;
    private final String createAt;
    private final String modifiedAt;
    private final String boardUsername;
//    private final String commentUsername;


    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.boardUsername = comment.getBoard().getMember().getUsername();


    }
}
