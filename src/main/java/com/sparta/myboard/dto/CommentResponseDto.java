package com.sparta.myboard.dto;


import com.sparta.myboard.entity.Comment;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommentResponseDto {
    private final Long id;
    private final String content;
    private final String createAt;
    private final String modifiedAt;
    private final String commentUsername;

    //리스트화 시켜서 붙힐 예정
    private List<CommentResponseDto> comments = new ArrayList<>();


    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.commentUsername = comment.getMember().getUsername();

        for (Comment child : comment.getChildren()) {
            comments.add(new CommentResponseDto(child));
        }
    }
}
