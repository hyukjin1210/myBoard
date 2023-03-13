package com.sparta.myboard.controller;

import com.sparta.myboard.dto.LoginRequestDto;
import com.sparta.myboard.dto.SignUpRequestDto;
import com.sparta.myboard.service.MemberService;
import com.sparta.myboard.status.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @ApiOperation(value="회원 가입", notes="회원 가입(비밀번호 암호화 후 저장)")
    @PostMapping("/signup")
    public ResponseEntity signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        memberService.signUp(signUpRequestDto);
//        log.info();
        return new ResponseEntity(new Response(StatusCode.CREATED, ResponseMessage.CREATED_USER), HttpStatus.OK);
    }

    @ApiOperation(value="로그인", notes="로그인(성공시 Jwt토큰 발급)")
    @GetMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        memberService.login(loginRequestDto, response);
        return new ResponseEntity(new Response(StatusCode.OK,
                ResponseMessage.LOGIN_SUCCESS), HttpStatus.OK);
    }

    @ApiOperation(value="잘못된 GET 요청", notes="잘못된 요청시 403 FORBIDDEN 상태코드 반환")
    @GetMapping("/forbidden")
    public ResponseEntity getForbidden() {
        return new ResponseEntity(new Response(StatusCode.FORBIDDEN,
                ResponseMessage.DB_ERROR),HttpStatus.FORBIDDEN);
    }

    @ApiOperation(value="잘못된 POST 요청", notes="잘못된 요청시 403 FORBIDDEN 상태코드 반환")
    @PostMapping("/forbidden")
    public ResponseEntity postForbidden() {
        return new ResponseEntity(new Response(StatusCode.FORBIDDEN,
                ResponseMessage.DB_ERROR),HttpStatus.FORBIDDEN);
    }

}