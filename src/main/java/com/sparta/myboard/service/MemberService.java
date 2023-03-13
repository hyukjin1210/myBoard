package com.sparta.myboard.service;

import com.sparta.myboard.dto.LoginRequestDto;
import com.sparta.myboard.dto.SignUpRequestDto;
import com.sparta.myboard.dto.MemberResponseDto;
import com.sparta.myboard.entity.UserRoleEnum;
import com.sparta.myboard.entity.Member;
import com.sparta.myboard.jwt.JwtUtil;
import com.sparta.myboard.repository.MemberRepository;
import com.sparta.myboard.status.CustomErrorCode;
import com.sparta.myboard.status.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "admin";

    @Transactional
    public void signUp(SignUpRequestDto signUpRequestDto) {
        Optional<Member> found = memberRepository.findByUsername(signUpRequestDto.getUsername());
        if (found.isPresent()) {
            throw new CustomException(CustomErrorCode.ALREADY_USED_ID);  //중복된 아이디 에러
        }

        UserRoleEnum role = UserRoleEnum.USER;
        if (signUpRequestDto.isAdmin()) {  //RequestDto의 admin = true 일 때 실행.
            if (!ADMIN_TOKEN.equals(signUpRequestDto.getAdminToken())) {
                throw new CustomException(CustomErrorCode.NOT_MATCHED_ADMINTOKEN);  //관리자 암호 불일치 에러
            } else {
                role = UserRoleEnum.ADMIN;
            }
        }
        Member member = new Member(signUpRequestDto, role);
        member.hashPassword(passwordEncoder);   //Member 에 저장한 비밀번호의 암호화 진행.
        Member save = memberRepository.save(member);
        new MemberResponseDto(save);
    }

    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        Member member = memberRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(
                () -> new CustomException(CustomErrorCode.NOT_FOUND_MEMBER));   //회원정보 없음 에러
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
            throw  new CustomException(CustomErrorCode.NOT_MATCHED_PASSWORD);   //비밀번호 오류 에러
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(member.getUsername(), member.getRole()));
    }

    public Member findMember (String username) {// claims를 이용해 멤버를 찾기 위한 메서드
        return memberRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(CustomErrorCode.NOT_FOUND_MEMBER));   //회원 정보 없음 에러
    }

}