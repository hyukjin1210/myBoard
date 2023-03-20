package com.sparta.myboard.controller;

import com.sparta.myboard.dto.HeartRequestDto;
import com.sparta.myboard.security.MemberDetailsImpl;
import com.sparta.myboard.service.HeartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;

    @PostMapping("/api/heart/{id}")
    public ResponseEntity<String> addHeart (@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                            @PathVariable Long id) {
        boolean result = false;
        if (memberDetails != null) {
            result = heartService.addHeart(memberDetails.getMember(), id);
        }
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
//    @PostMapping("/api/heart")
//    public String likeBoard (@RequestBody @Valid HeartRequestDto requestDto,
//                             @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
//        heartService.likeBoard(requestDto, memberDetails);
//        return "좋아요";
//    }

    @DeleteMapping("/api/heart")
    public String disLikeBoard (@RequestBody HeartRequestDto requestDto,
                                @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        heartService.disLikeBoard(requestDto, memberDetails);
        return "좋아요 취소";
    }
}
