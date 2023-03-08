package com.sparta.myboard.entity;

import com.sparta.myboard.dto.SignUpRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AuthEnum auth;

    // Member 와 Board 의 관계 = 1 : N
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
//    @JoinColumn(name = "MEMBER_ID")   //FK
    // 조인컬럼이 없으면 중간테이블을 jpa가 만들어서 원하는 조회가 안된다.
    List<Board> boards = new ArrayList<>();

    public Member(SignUpRequestDto memberRequestDto, AuthEnum auth) {
        this.username = memberRequestDto.getUsername();
        this.email = memberRequestDto.getEmail();
        this.password = memberRequestDto.getPassword();
        this.auth = auth;
    }

}
/*
mappedBy로 연관관계의 주인을 정해주어야 한다.
데이터베이스에서는 '다'쪽이 연관관계의 주인이 된다.
Member 테이블은 연관관계 주인인 Board 테이블의 "member" 필드에 해당한다.
*/