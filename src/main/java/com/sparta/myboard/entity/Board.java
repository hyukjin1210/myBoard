package com.sparta.myboard.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sparta.myboard.dto.BoardRequestDto;
import com.sparta.myboard.dto.BoardUpdateRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
@NoArgsConstructor
public class Board extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long id;
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;


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
        this.member = member;
    }

    public void update(BoardUpdateRequestDto boardUpdateRequestDto) {
        this.title = boardUpdateRequestDto.getTitle();
        this.contents = boardUpdateRequestDto.getContents();
    }

}
