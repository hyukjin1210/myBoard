package com.sparta.myboard.service;

import com.sparta.myboard.dto.BoardRequestDto;
import com.sparta.myboard.dto.BoardResponseDto;
import com.sparta.myboard.dto.BoardUpdateRequestDto;
import com.sparta.myboard.entity.Board;
import com.sparta.myboard.entity.Member;
import com.sparta.myboard.jwt.JwtUtil;
import com.sparta.myboard.repository.BoardRepository;
import com.sparta.myboard.repository.MemberRepository;
import com.sparta.myboard.status.CustomErrorCode;
import com.sparta.myboard.status.CustomException;
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
    private final MemberService memberService;
    private final JwtUtil jwtUtil;

//    @Transactional    //조인컬럼을 사용하지 않는경우
//    public BoardResponseDto createBoard(BoardRequestDto requestDto, HttpServletRequest request) {
//        String username = memberService.tokenChk(request).getUsername();
//
//        Board board = new Board(requestDto, username);
//        Board save = boardRepository.saveAndFlush(board);
//
//        return new BoardResponseDto(save);
//    }

    @Transactional  //조인컬럼을 사용하는 경우
    public BoardResponseDto createBoard(BoardRequestDto requestDto, HttpServletRequest request) {
        String username = memberService.tokenChk(request).getUsername();
        Member member = new Member();
//        List<Board> members = member.getUsername();

        Board board = new Board(requestDto, member);    //이건 유저네임을 어떻게 찾아야 되지
        Board save = boardRepository.saveAndFlush(board);

        return new BoardResponseDto(save);
    }

    @Transactional(readOnly = true)
    public BoardResponseDto getBoard(Long id) {
        return new BoardResponseDto(boardRepository.findById(id).orElseThrow(
                () -> new CustomException(CustomErrorCode.NOT_FOUND_BOARD)));
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> boardList() {
        List<Board> boardlist = boardRepository.findAllByOrderByCreatedAtDesc();
        List<BoardResponseDto> ResponseList = new ArrayList<>();

        for (Board board : boardlist) {
            BoardResponseDto boardResponseDto = new BoardResponseDto(board);
            ResponseList.add(boardResponseDto);
        }
        return ResponseList;
    }

    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardUpdateRequestDto boardUpdateRequestDto,
                                        HttpServletRequest request) {
        Board board = findAndCheck(id);

        String username = memberService.tokenChk(request).getUsername();

        if (board.getUsername().equals(username)) {
            board.update(boardUpdateRequestDto);

            return new BoardResponseDto(board);

        } else {
            throw new CustomException(CustomErrorCode.REQUIRED_LOGIN);
        }

    }

    @Transactional
    public void deleteBoard(Long id, HttpServletRequest request) {
        Board board = findAndCheck(id);

        String username = memberService.tokenChk(request).getUsername();

        if (board.getUsername().equals(username)) {

            boardRepository.deleteById(id);

        } else {
            throw new CustomException(CustomErrorCode.REQUIRED_LOGIN);
        }
    }

    private Board findAndCheck(Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new CustomException(CustomErrorCode.NOT_FOUND_BOARD));
    }

}
