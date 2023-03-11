package com.sparta.myboard.controller;

import com.sparta.myboard.dto.BoardRequestDto;
import com.sparta.myboard.dto.BoardResponseDto;
import com.sparta.myboard.dto.BoardUpdateRequestDto;
import com.sparta.myboard.entity.Member;
import com.sparta.myboard.jwt.JwtUtil;
import com.sparta.myboard.repository.MemberRepository;
import com.sparta.myboard.service.BoardService;
import com.sparta.myboard.status.*;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardService boardService;
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    @ApiOperation(value="게시물 작성", notes="게시물 작성(토큰검사, 유효성검사)")
    @PostMapping("/create")
    public BoardResponseDto createBoard(@Valid @RequestBody BoardRequestDto requestDto, HttpServletRequest request) {
//        tokenChkOnly(request); //토큰체크를 컨트롤러에서 한다해도 서비스간의 연관관계를 끊을 방법이 없음.
        return boardService.createBoard(requestDto, request);
    }

    @ApiOperation(value="게시물 전체 조회", notes="게시물 전체 조회")
    @GetMapping("/boardList")
    public List<BoardResponseDto> boardList() {
        return boardService.boardList();
    }

    @ApiOperation(value="게시물 조회", notes="선택한 게시물 조회")
    @GetMapping("/getBoard/")
    public BoardResponseDto getBoard(@RequestParam Long id) {
        return boardService.getBoard(id);
    }

    @ApiOperation(value="게시물 수정", notes="게시물 수정(토큰검사, 관리자 권한 체크)")
    @PutMapping("/update/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id,
                                        @Validated @RequestBody BoardUpdateRequestDto boardUpdateRequestDto,
                                        HttpServletRequest request) {
        return boardService.updateBoard(id, boardUpdateRequestDto, request);
    }

    @ApiOperation(value="게시물 삭제", notes="게시물 삭제(토큰검사, 관리자 권한 체크)")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteBoard(@PathVariable Long id, HttpServletRequest request) {
        boardService.deleteBoard(id, request);
        return new ResponseEntity(new Response(StatusCode.OK,
                ResponseMessage.DELETE_BOARD), HttpStatus.OK);
    }

//    public void tokenChkOnly(HttpServletRequest request) {
//        String token = jwtUtil.resolveToken(request);
//
//        if (token == null || !jwtUtil.validateToken(token)) {
//            throw new CustomException(CustomErrorCode.NOT_VALID_TOKEN); //유효하지 않은 토큰 에러
//        }
//    }

}

