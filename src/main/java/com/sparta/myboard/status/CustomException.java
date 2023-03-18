package com.sparta.myboard.status;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class CustomException extends RuntimeException{
    private final CustomErrorCode customErrorCode;
    private final String detailMessage;
    private final int statusCode;

    public CustomException(CustomErrorCode customErrorCode) {
        super(customErrorCode.getStatusMessage());
        this.customErrorCode = customErrorCode;
        this.detailMessage = customErrorCode.getStatusMessage();
        this.statusCode = customErrorCode.getStatusCode();
    }
}
