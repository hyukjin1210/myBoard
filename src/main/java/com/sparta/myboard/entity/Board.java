package com.sparta.myboard.entity;

import com.sparta.myboard.dto.BoardRequestDto;
import com.sparta.myboard.dto.BoardUpdateRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@DynamicInsert
public class Board extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long id;
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @ColumnDefault("0")
    @Column(nullable = false)
    private int viewCount;




    // Board 와 Member 의 관계 = N : 1
    @ManyToOne
    @JoinColumn(name="MEMBER_ID")   //이게 FK의 역할
    private Member member;  // id 1개가 저장되는 것이다.

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    List<Comment> comments = new ArrayList<>();


//    public Board(BoardRequestDto requestDto, String username) {
//        this.title = requestDto.getTitle();
//        this.contents = requestDto.getContents();
//        this.username = username;
//    }

    public Board(BoardRequestDto requestDto, Member member) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.viewCount = 0;
        this.member = member;
    }

    public void update(BoardUpdateRequestDto boardUpdateRequestDto) {
        this.title = boardUpdateRequestDto.getTitle();
        this.contents = boardUpdateRequestDto.getContents();
    }

//    public void viewCountUp (Board board) {
//        board.viewCount++;
//    }

}
