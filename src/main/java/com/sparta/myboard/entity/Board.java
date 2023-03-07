package com.sparta.myboard.entity;

import com.sparta.myboard.dto.BoardRequestDto;
import com.sparta.myboard.dto.BoardUpdateRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Entity
//@Where(clause = "deleted_at IS NULL")
@NoArgsConstructor
public class Board extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long id;
    @Column(nullable = false)
    private String title;

//    @Column(nullable = false)
//    private String writer;

//    @Column(nullable = false)
//    private String password;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String username;

//    @ManyToOne
//    @JoinColumn(name="MEMBER_ID")
//    private Member member;


    public Board(BoardRequestDto requestDto, String username) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.username = username;
    }

    public void update(BoardUpdateRequestDto boardUpdateRequestDto) {
        this.title = boardUpdateRequestDto.getTitle();
        this.contents = boardUpdateRequestDto.getContents();
    }

}
