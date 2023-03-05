package com.sparta.myboard.service;

import com.sparta.myboard.dto.BoardRequestDto;
import com.sparta.myboard.dto.BoardResponseDto;
import com.sparta.myboard.dto.BoardUpdateRequestDto;
import com.sparta.myboard.entity.Board;
import com.sparta.myboard.entity.Member;
import com.sparta.myboard.jwt.JwtUtil;
import com.sparta.myboard.repository.BoardRepository;
import com.sparta.myboard.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public BoardResponseDto insertBoard(BoardRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if(token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰이 없습니다.");
            }
            Member member = memberRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
            );

            String username = member.getUsername();
            Board board = boardRepository.saveAndFlush(new Board(requestDto, username));
            return new BoardResponseDto(board);
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public BoardResponseDto findBoardOne(Long id) {
        return new BoardResponseDto(boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("회원 상세 조회 실패")));
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getBoardListAll() {
        List<Board> boardlist = boardRepository.findAllByOrderByCreatedAtDesc();
        List<BoardResponseDto> ResponseList = new ArrayList<>();

        for (Board board  : boardlist) {
            BoardResponseDto boardResponseDto = new BoardResponseDto(board);
            ResponseList.add(boardResponseDto);
        }
        return ResponseList;
    }

//    @Transactional(readOnly = true)
//    public BoardResponseDto findBoardOne(Long id) {
//        return new BoardResponseDto(boardRepository.findById(id).orElseThrow(
//                () -> new NullPointerException("회원 상세 조회 실패")));
//    }

    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardUpdateRequestDto boardUpdateRequestDto,
                                        HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if(token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰이 없습니다.");
            }
            Member member = memberRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
            );


            Board board = findAndCheck(id);
            if (board.getUsername().equals(member.getUsername())) {
                board.update(boardUpdateRequestDto);

                return new BoardResponseDto(board);
            } else {
                throw new IllegalArgumentException("아이디가 맞지 않습니다.");
            }


        } else {
            return null;
        }

    }
//
//    @Transactional
//    public String deleteBoard(Long id, BoardRequestDto requestDto) throws Exception {
//        Board board = findAndCheck(id);
//        if (!board.getPassword().equals(requestDto.getPassword())) {
//            throw new Exception("비밀번호가 일치하지 않습니다");
//        } else {
//            boardRepository.deleteById(id);
//
//        }
//        return board.getWriter() + "님의 글이 삭제 되었습니다.";
//    }


    private Board findAndCheck(Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
    }
}
