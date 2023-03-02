package com.sparta.myboard.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class MemberRequestDto {
    @NotBlank(message = "아이디는 공백이 될 수 없습니다.")
    String name;
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    String email;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
    message = " 비밀번호는 최소 8글자, 글자 1개, 숫자 1개, 특수문자 1개를 포함하여 주세요.")
    String pw;

}
