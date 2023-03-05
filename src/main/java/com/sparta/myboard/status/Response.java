package com.sparta.myboard.status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Response {
    private String msg;
    private int statusCode;
//    private T data;

    public Response(final int statusCode, final String responseMessage) {
        this.statusCode = statusCode;
        this.msg = responseMessage;
//        this.data = null;
    }

//    public static<T> DefaultRes<T> res(final int statusCode, final String responseMessage) {
//        return res(statusCode, responseMessage, null);
//    }
//
//    public static<T> DefaultRes<T> res(final int statusCode, final String responseMessage, final T t) {
//        return DefaultRes.<T>builder()
//                .data(t)
//                .statusCode(statusCode)
//                .msg(responseMessage)
//                .build();
//    }
}