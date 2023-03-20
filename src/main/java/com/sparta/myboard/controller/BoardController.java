package com.sparta.myboard.controller;

import com.sparta.myboard.dto.BoardRequestDto;
import com.sparta.myboard.dto.BoardResponseDto;
import com.sparta.myboard.dto.BoardUpdateRequestDto;
import com.sparta.myboard.security.MemberDetailsImpl;
import com.sparta.myboard.service.BoardService;
import com.sparta.myboard.status.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;


//    @ApiOperation(value="게시물 작성", notes="게시물 작성(토큰검사)")
    @PostMapping("/api/boards")
    public BoardResponseDto createBoard(@Valid @RequestBody BoardRequestDto requestDto,
                                        @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return boardService.createBoard(requestDto, memberDetails.getMember());
    }

//    @ApiOperation(value="게시물 전체 조회", notes="게시물 전체 조회")
    @GetMapping("/api/boards")
    public List<BoardResponseDto> boardList() {
        return boardService.boardList();
    }

//    @ApiOperation(value="게시물 조회", notes="선택한 게시물 조회")
    @GetMapping("/api/boards/{id}")
    public BoardResponseDto getBoard(@PathVariable Long id) {
        boardService.updateView(id);    //조회수 증가
        return boardService.getBoard(id);
    }

//    @ApiOperation(value="게시물 수정", notes="게시물 수정(토큰검사, 권한 확인)")
    @PutMapping("/api/boards/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id,
                                        @Validated @RequestBody BoardUpdateRequestDto boardUpdateRequestDto,
                                        @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return boardService.updateBoard(id, boardUpdateRequestDto, memberDetails.getMember());
    }

//    @ApiOperation(value="게시물 삭제", notes="게시물 삭제(토큰검사, 권한 확인)")
    @DeleteMapping("/api/boards/{id}")
    public ResponseEntity deleteBoard(@PathVariable Long id,
                                      @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        boardService.deleteBoard(id, memberDetails.getMember());
        return new ResponseEntity(new Response(StatusCode.OK,
                ResponseMessage.DELETE_BOARD), HttpStatus.OK);
    }

}

