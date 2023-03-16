package com.sparta.myboard.entity;

import com.sparta.myboard.dto.CommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor

public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 양방향 셀프조인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    //양방향 셀프조인
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();





    public Comment(CommentRequestDto commentDto, Member member) {
        this.content = commentDto.getContent();
        this.username = member.getUsername();
        this.member = member;
    }

    public Comment (CommentRequestDto commentDto, Member member, Comment parent) {
        this.content = commentDto.getContent();
        this.username = member.getUsername();
        this.member = member;
        this.parent = parent;
    }

    // 댓글 수정
    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }

    // 부모 댓글 수정
    public void updateParent(Comment parent){
        this.parent = parent;
    }

}
