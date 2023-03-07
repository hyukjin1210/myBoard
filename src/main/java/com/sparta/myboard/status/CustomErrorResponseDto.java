package com.sparta.myboard.status;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CustomErrorResponseDto{
    private CustomErrorCode status;
    private String statusMessage;
}