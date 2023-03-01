package com.sparta.myboard.dto;

import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
public class BoardRequestDto {
    @NotBlank(message = "제목입력은 필수 입니다.")
    private String title;
    @NotEmpty(message = "{required.user.userName}")
    private String writer;
    @Size(min = 5, max = 10, message = "비밀번호는 5자리 ~ 10자리 이어야 합니다.")
    private String password;
    @Size(max = 10, message = "내용은 10글자를 넘지 않게 작성해주세요")
    private String contents;

}
