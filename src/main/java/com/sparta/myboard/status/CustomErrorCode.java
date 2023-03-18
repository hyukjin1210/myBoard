package com.sparta.myboard.status;

import lombok.Getter;

@Getter
public enum CustomErrorCode {
    NOT_FOUND_MEMBER("회원 정보가 없습니다.", 400),
    NOT_MATCHED_PASSWORD("비밀번호가 맞지 않습니다.", 400),
    ALREADY_USED_ID("중복된 아이디가 존재합니다.", 400),
    NOT_FOUND_BOARD("게시물이 없습니다.", 400),
    NOT_VALID_TOKEN("토큰이 유효하지 않습니다.", 400),
    NOT_FOUND_COMMENT("댓글이 없습니다.", 400),
    NOT_THE_AUTHOR("작성자만 삭제/수정할 수 있습니다", 400),
    NOT_MATCHED_ADMINTOKEN("관리자 암호가 맞지 않습니다.", 400),
    DUPLICATE_LIKE("좋아요는 중복이 불가능 합니다.", 400),
    NOT_FOUND_HEART("좋아요가 없습니다.", 400),
    REQUIRED_LOGIN("로그인이 필요한 서비스 입니다.", 400);


    private final String statusMessage;
    private final int statusCode;

    CustomErrorCode(String statusMessage, int statusCode) {
        this.statusMessage = statusMessage;
        this.statusCode = statusCode;
    }

}
