package com.sparta.myboard.dto;

import com.sparta.myboard.entity.Board;
import com.sparta.myboard.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private final Long id;
    private final String content;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;
    private final String boardUsername;
    private final String commentUsername;


    public CommentResponseDto(Comment comment, String username) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.boardUsername = comment.getBoard().getMember().getUsername();
        this.commentUsername = username;

    }
}
