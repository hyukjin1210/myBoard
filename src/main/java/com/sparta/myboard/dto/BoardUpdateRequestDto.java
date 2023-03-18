package com.sparta.myboard.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class BoardUpdateRequestDto {
    @NotBlank(message = "제목입력은 필수 입니다.")
    private String title;
    @NotBlank
    @Size(min = 10, message = "내용은 10글자를 넘게 수정해주세요")
    private String contents;
}
