package com.sparta.myboard.entity;

import com.sparta.myboard.dto.MemberRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String pw;

    public Member(MemberRequestDto memberRequestDto) {
        this.name = memberRequestDto.getName();
        this.email = memberRequestDto.getEmail();
        this.pw = memberRequestDto.getPw();
    }

}
