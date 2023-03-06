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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Member(SignUpRequestDto memberRequestDto, AuthEnum auth) {
        this.username = memberRequestDto.getUsername();
        this.email = memberRequestDto.getEmail();
        this.password = memberRequestDto.getPassword();
        this.auth = auth;
    }

}
