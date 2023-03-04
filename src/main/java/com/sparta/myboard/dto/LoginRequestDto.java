package com.sparta.myboard.dto;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class LoginRequestDto {
    private String username;
    private String password;

}
