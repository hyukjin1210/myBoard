package com.sparta.myboard.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class HeartRequestDto {

    @NotNull(message = "필수 입력 값")
    private Long memberId;

    @NotNull(message = "필수 입력 값")
    private Long boardId;

//    public HeartRequestDto(Long memberId, Long boardId) {
//        this.memberId = memberId;
//        this.boardId = boardId;
//    }
}
