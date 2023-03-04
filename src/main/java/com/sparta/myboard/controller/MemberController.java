package com.sparta.myboard.controller;

import com.sparta.myboard.dto.LoginRequestDto;
import com.sparta.myboard.dto.MemberLoginResponseDto;
import com.sparta.myboard.dto.SignUpRequestDto;
import com.sparta.myboard.dto.MemberResponseDto;
import com.sparta.myboard.service.MemberService;
import com.sparta.myboard.status.DefaultRes;
import com.sparta.myboard.status.ResponseMessage;
import com.sparta.myboard.status.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/members")
    public ResponseEntity signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        memberService.signUp(signUpRequestDto);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK,
                ResponseMessage.CREATED_USER, signUpRequestDto), HttpStatus.OK);
    }

    //로그인
    @GetMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        memberService.login(loginRequestDto, response);
        return "상태코드반환 생각해라";
    }

    @GetMapping("/member")
    public MemberResponseDto getMemberInfo(@RequestParam Long id) {
       return memberService.getMember(id);
    }

    @GetMapping("/members")
    public List<MemberResponseDto> getMemberList() {
       return memberService.getMemberList();
    }

}