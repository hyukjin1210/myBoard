package com.sparta.myboard.status;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public CustomErrorResponseDto handleException(CustomException e, HttpServletRequest request) {

        log.error("errorCode : {}, url {}, message: {}, statusCode: {}",
                e.getCustomErrorCode(), request.getRequestURI(), e.getDetailMessage(), e.getStatusCode());

        return CustomErrorResponseDto.builder()
                .status(e.getCustomErrorCode())
                .statusMessage(e.getDetailMessage())
                .statusCode(e.getStatusCode())
                .build();
    }
}
