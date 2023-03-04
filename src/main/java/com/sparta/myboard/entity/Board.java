package com.sparta.myboard.entity;

import com.sparta.myboard.dto.BoardRequestDto;
import com.sparta.myboard.dto.BoardUpdateRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Entity
//@Where(clause = "deleted_at IS NULL")
@NoArgsConstructor
public class Board extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String contents;


    public Board(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.writer = requestDto.getWriter();
        this.password = requestDto.getPassword();
        this.contents = requestDto.getContents();
    }

    public void update(BoardUpdateRequestDto boardUpdateRequestDto) {
        this.title = boardUpdateRequestDto.getTitle();
        this.writer = boardUpdateRequestDto.getWriter();
        this.contents = boardUpdateRequestDto.getContents();
    }

}
