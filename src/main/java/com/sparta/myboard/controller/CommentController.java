package com.sparta.myboard.controller;

import com.sparta.myboard.dto.CommentRequestDto;
import com.sparta.myboard.dto.CommentResponseDto;
import com.sparta.myboard.jwt.JwtUtil;
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
public class CommentController {
    private final CommentService commentService;
    private final JwtUtil jwtUtil;

//    @ApiOperation(value="댓글 작성", notes="댓글 작성(토큰검사, 권한 확인)")
//    @PostMapping("/api/comment/{id}")
//    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto,
//                                            @PathVariable Long id, HttpServletRequest request) {
//        return commentService.createComment(requestDto, id, jwtUtil.loadToken(request));
//    }

    @PostMapping("/api/comment/")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto,
                                             HttpServletRequest request) {
        return commentService.createComment(requestDto, jwtUtil.loadToken(request));
    }

//    @ApiOperation(value="댓글 수정", notes="댓글 작성(토큰검사, 본인 확인, 권한 확인)")
    @PutMapping("/api/comments/{id}")
    public CommentResponseDto updateComment(@RequestBody CommentRequestDto requestDto,
                                            @PathVariable Long id, HttpServletRequest request) {
        return commentService.updateComment(requestDto, id, jwtUtil.loadToken(request));
    }

//    @ApiOperation(value="댓글 삭제", notes="댓글 작성(토큰검사, 본인 확인, 권한 확인)")
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity deleteComment(@PathVariable Long id, HttpServletRequest request) {
        commentService.deleteComment(id, jwtUtil.loadToken(request));
        return new ResponseEntity(new Response(StatusCode.OK,
                ResponseMessage.DELETE_COMMENT), HttpStatus.OK);
    }

}
