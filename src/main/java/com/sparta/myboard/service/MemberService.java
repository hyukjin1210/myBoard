package com.sparta.myboard.service;

import com.sparta.myboard.dto.MemberRequestDto;
import com.sparta.myboard.dto.MemberResponseDto;
import com.sparta.myboard.entity.Member;
import com.sparta.myboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponseDto createMember(MemberRequestDto memberRequestDto) {
        Member member = new Member(memberRequestDto);
        memberRepository.save(member);
        return new MemberResponseDto(member);
    }

    @Transactional
    public MemberResponseDto getMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new NullPointerException("회원 상세 조회 실패"));
        return new MemberResponseDto(member);

    }

    @Transactional
    public List<MemberResponseDto> getMemberList() {
        List<Member> members = memberRepository.findAll();
//        List<MemberResponseDto> membersList = new ArrayList<>();

        return members.stream()
                .map(member -> new MemberResponseDto(member)).toList();
    }
}