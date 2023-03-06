//package com.sparta.myboard.service;
//
//import com.sparta.myboard.entity.Member;
//import io.jsonwebtoken.Claims;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Optional;
//
//public class Service_Tool {
//    private boolean tokenChk(HttpServletRequest request) {
//        String token = jwtUtil.resolveToken(request);
//        Claims claims;
//        if (jwtUtil.validateToken(token)) {
//            claims = jwtUtil.getUserInfoFromToken(token);
//        } else {
//            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
//        }
//        return true;
//    }
//}
