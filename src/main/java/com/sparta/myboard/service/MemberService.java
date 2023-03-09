package com.sparta.myboard.service;

import com.sparta.myboard.dto.LoginRequestDto;
import com.sparta.myboard.dto.SignUpRequestDto;
import com.sparta.myboard.dto.MemberResponseDto;
import com.sparta.myboard.entity.AuthEnum;
import com.sparta.myboard.entity.Member;
import com.sparta.myboard.jwt.JwtUtil;
import com.sparta.myboard.repository.MemberRepository;
import com.sparta.myboard.status.CustomErrorCode;
import com.sparta.myboard.status.CustomException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "admin";

    @Transactional
    public void signUp(SignUpRequestDto signUpRequestDto) {
        Optional<Member> found = memberRepository.findByUsername(signUpRequestDto.getUsername());
        if (found.isPresent()) {
            throw new CustomException(CustomErrorCode.ALREADY_USED_ID);  //수정4번
        }

        AuthEnum auth = AuthEnum.USER;
        if (signUpRequestDto.isAdmin()) {  //RequestDto의 admin = false 일 때 실행.
            if (!signUpRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new CustomException(CustomErrorCode.NOT_MATCHED_ADMINTOKEN);
            } else {
                auth = AuthEnum.ADMIN;
            }
        }
        Member member = new Member(signUpRequestDto, auth);
        Member save = memberRepository.save(member);
        new MemberResponseDto(save);
    }

    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        Member member = memberRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(
                () -> new CustomException(CustomErrorCode.NOT_FOUND_MEMBER));
        if (!member.getPassword().equals(loginRequestDto.getPassword())) {
            throw  new CustomException(CustomErrorCode.NOT_MATCHED_PASSWORD);
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(member.getUsername(), member.getAuth()));
    }

    @Transactional
    public MemberResponseDto getMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new CustomException(CustomErrorCode.NOT_FOUND_MEMBER));
        return new MemberResponseDto(member);

    }

    @Transactional
    public List<MemberResponseDto> getMemberList() {
        List<Member> members = memberRepository.findAll();

        return members.stream()
                .map(member -> new MemberResponseDto(member)).toList();
    }

    public Member tokenChk(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);

        if (token == null || !jwtUtil.validateToken(token)) {
            throw new CustomException(CustomErrorCode.NOT_VALID_TOKEN);
        }

        Claims claims = jwtUtil.getUserInfoFromToken(token);

        return memberRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new CustomException(CustomErrorCode.NOT_FOUND_MEMBER));
    }
}