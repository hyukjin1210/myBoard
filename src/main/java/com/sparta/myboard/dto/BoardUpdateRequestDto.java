package com.sparta.myboard.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
public class BoardUpdateRequestDto {
    @NotBlank(message = "제목입력은 필수 입니다.")
    private String title;
    @NotEmpty(message = "작성자명은 공백이 올 수 없습니다.")
    private String writer;
    @Size(min = 5, max = 10, message = "비밀번호는 5자리 ~ 10자리 이어야 합니다.")
    private String password;
    @Size(min = 10, message = "내용은 10글자를 넘게 수정해주세요")
    private String contents;
}
