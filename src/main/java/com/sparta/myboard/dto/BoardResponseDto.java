package com.sparta.myboard.dto;

import com.sparta.myboard.entity.Board;
import com.sparta.myboard.entity.Comment;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class BoardResponseDto {
    private final Long id;
    private final String title;
    private final String contents;
    private final String username;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;
    private final List<CommentResponseDto> commentList = new ArrayList<>();

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.username = board.getMember().getUsername();
        this.createAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();

        for(Comment comment : board.getComments()) {
            commentList.add(new CommentResponseDto(comment));
        }

    }
}
/*
유저네임을 board.getMember().getUsername() 이렇게 불러올 수 있는 이유 :
1. 멤버와 보드의 연관관계를 맺어주었다
2. 보드를 생성할 때 멤버의 데이터 전체를 저장하지만,
뷰로 보여줄 때 사용하는것은 BoardResponseDto이기 때문에
여기서 내가 원하는 정보를 getMember().getUsername으로 불러올 수 있다.

*/