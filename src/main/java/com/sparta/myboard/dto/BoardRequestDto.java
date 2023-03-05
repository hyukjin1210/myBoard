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
//    @NotEmpty(message = "작성자명은 공백이 올 수 없습니다.")
//    private String writer;
//    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
//            message = " 비밀번호는 최소 8글자, 글자 1개, 숫자 1개, 특수문자 1개를 포함하여 주세요.")
//    private String password;
    @NotBlank(message = "내용입력은 필수 입니다.")
    private String contents;

}
