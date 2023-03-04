package com.sparta.myboard.service;

import com.sparta.myboard.dto.LoginRequestDto;
import com.sparta.myboard.dto.MemberLoginResponseDto;
import com.sparta.myboard.dto.SignUpRequestDto;
import com.sparta.myboard.dto.MemberResponseDto;
import com.sparta.myboard.entity.Member;
import com.sparta.myboard.jwt.JwtUtil;
import com.sparta.myboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signUp(SignUpRequestDto signUpRequestDto) {
        Optional<Member> found = memberRepository.findByUsername(signUpRequestDto.getUsername());
        if(found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        Member member = new Member(signUpRequestDto);
        memberRepository.save(member);
        new MemberResponseDto(member);
    }

    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        Member member = memberRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("등록된 아이디가 없습니다."));
        if (!member.getPassword().equals(loginRequestDto.getPassword())) {
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(member.getUsername(), member.getEmail()));
//        파라미터에 Http 추가하고, jwt 토큰발급이 이루어지면 끝
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