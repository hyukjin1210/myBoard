package com.sparta.myboard.status;

import lombok.Getter;

@Getter
public enum CustomErrorCode {
    NOT_FOUND_MEMBER("회원 정보가 없습니다."),
    NOT_MATCHED_PASSWORD("비밀번호가 맞지 않습니다."),
    ALREADY_USED_ID("중복된 아이디가 존재합니다."),
    NOT_FOUND_BOARD("게시물이 없습니다."),
    NOT_VALID_TOKEN("토큰이 유효하지 않습니다."),
    REQUIRED_LOGIN("로그인이 필요한 서비스 입니다.");


    private final String statusMessage;

    CustomErrorCode(String statusMessage) {
        this.statusMessage = statusMessage;
    }

}
