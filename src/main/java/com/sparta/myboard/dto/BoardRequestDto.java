package com.sparta.myboard.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class BoardRequestDto {
    @NotBlank(message = "제목입력은 필수 입니다.")
    @Size(max = 10, message = "제목은 10글자를 넘지 않게 작성해주세요")
    private String title;
    @NotBlank(message = "내용입력은 필수 입니다.")
    private String contents;

}
