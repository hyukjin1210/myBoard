package com.sparta.myboard.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Heart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heart_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "board_id")
    private Board board;

    public Heart(Member member, Board board) {
        this.member = member;
        this.board = board;
    }
}
/*
좋아요는 멤버와 N : 1 관계이다.
-> 한명의 회원은 여러개의 좋아요를 할 수 있다.
좋아요는 보드와 N : 1 관계이다.
-> 한개의 게시물은 여러개의 좋아요가 있을 수 있다.

DB입장에서 관계를 맺는다는 것은 제약을 건다는 것이다.
->
select
*/