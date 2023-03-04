package com.sparta.myboard.dto;

import com.sparta.myboard.entity.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private Long id;
    private String username;
    private String email;
    private String password;


    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.password = member.getPassword();
    }


}
