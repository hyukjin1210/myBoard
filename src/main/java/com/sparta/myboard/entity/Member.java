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

//    @OneToMany(mappedBy = member)
//    private List<Board> boards = new ArrayList<>();

    public Member(SignUpRequestDto memberRequestDto, AuthEnum auth) {
        this.username = memberRequestDto.getUsername();
        this.email = memberRequestDto.getEmail();
        this.password = memberRequestDto.getPassword();
        this.auth = auth;
    }

}
