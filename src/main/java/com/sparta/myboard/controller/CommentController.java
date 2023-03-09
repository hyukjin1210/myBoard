package com.sparta.myboard.controller;

import com.sparta.myboard.dto.CommentRequestDto;
import com.sparta.myboard.dto.CommentResponseDto;
import com.sparta.myboard.entity.Board;
import com.sparta.myboard.service.CommentService;
import com.sparta.myboard.status.Response;
import com.sparta.myboard.status.ResponseMessage;
import com.sparta.myboard.status.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment/{id}")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto,
                                         @PathVariable Long id,
                                         HttpServletRequest request) {
        return commentService.createComment(requestDto, id, request);
    }

    @PutMapping("/comment/{id}")
    public CommentResponseDto updateComment(@RequestBody CommentRequestDto requestDto,
                                            @PathVariable Long id,
                                            HttpServletRequest request) {
    return commentService.updateComment(requestDto, id, request);
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity deleteComment(@PathVariable Long id, HttpServletRequest request) {
        commentService.deleteComment(id, request);
        return new ResponseEntity(new Response(StatusCode.OK,
                ResponseMessage.DELETE_COMMENT), HttpStatus.OK);
    }


}
