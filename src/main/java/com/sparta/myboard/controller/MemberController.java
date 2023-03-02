package com.sparta.myboard.controller;

import com.sparta.myboard.dto.MemberRequestDto;
import com.sparta.myboard.dto.MemberResponseDto;
import com.sparta.myboard.entity.Member;
import com.sparta.myboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public MemberResponseDto createMember(@Valid @RequestBody MemberRequestDto memberRequestDto) {

        return  memberService.createMember(memberRequestDto);
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