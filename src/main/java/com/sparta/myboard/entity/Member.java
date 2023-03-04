package com.sparta.myboard.entity;

import com.sparta.myboard.dto.SignUpRequestDto;
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
    private String username;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    public Member(SignUpRequestDto memberRequestDto) {
        this.username = memberRequestDto.getUsername();
        this.email = memberRequestDto.getEmail();
        this.password = memberRequestDto.getPassword();
    }

}
