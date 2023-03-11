package com.sparta.myboard.controller;

import com.sparta.myboard.dto.LoginRequestDto;
import com.sparta.myboard.dto.SignUpRequestDto;
import com.sparta.myboard.dto.MemberResponseDto;
import com.sparta.myboard.entity.AuthEnum;
import com.sparta.myboard.entity.Member;
import com.sparta.myboard.jwt.JwtUtil;
import com.sparta.myboard.repository.MemberRepository;
import com.sparta.myboard.service.MemberService;
import com.sparta.myboard.status.*;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;


    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        memberService.signUp(signUpRequestDto);
//        log.info();
        return new ResponseEntity(new Response(StatusCode.CREATED, ResponseMessage.CREATED_USER), HttpStatus.OK);
    }

    //로그인
    @GetMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        memberService.login(loginRequestDto, response);
        return new ResponseEntity(new Response(StatusCode.OK,
                ResponseMessage.LOGIN_SUCCESS), HttpStatus.OK);
    }

    @GetMapping("/member")
    public MemberResponseDto getMemberInfo(@RequestParam Long id) {
        return memberService.getMember(id);
    }

    @GetMapping("/memberList")
    public List<MemberResponseDto> getMemberList() {
        return memberService.getMemberList();
    }

}