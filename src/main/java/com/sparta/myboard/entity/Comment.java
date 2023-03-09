package com.sparta.myboard.entity;

import com.sparta.myboard.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long id;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;


    public Comment(CommentRequestDto commentDto, Board board) {
        this.content = commentDto.getContent();
        this.board = board;
    }

    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
