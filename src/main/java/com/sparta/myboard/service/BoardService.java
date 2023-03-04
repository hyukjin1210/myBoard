package com.sparta.myboard.service;

import com.sparta.myboard.dto.BoardRequestDto;
import com.sparta.myboard.dto.BoardResponseDto;
import com.sparta.myboard.dto.BoardUpdateRequestDto;
import com.sparta.myboard.entity.Board;
import com.sparta.myboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public BoardResponseDto insertBoard(BoardRequestDto requestDto) {
        Board board = new Board(requestDto);
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getBoardListAll() {
        List<Board> boardlist = boardRepository.findAllByOrderByCreateAtDesc();
        List<BoardResponseDto> ResponseList = new ArrayList<>();

        for (Board board  : boardlist) {
            BoardResponseDto boardResponseDto = new BoardResponseDto(board);
            ResponseList.add(boardResponseDto);
        }
        return ResponseList;
    }

    @Transactional(readOnly = true)
    public BoardResponseDto findBoardOne(Long id) {
        return new BoardResponseDto(boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("회원 상세 조회 실패")));
    }

    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardUpdateRequestDto boardUpdateRequestDto) throws Exception {
        Board board = findAndCheck(id);
        if (!board.getPassword().equals(boardUpdateRequestDto.getPassword())) {
            throw new Exception("비밀번호가 일치하지 않습니다");
        } else {
            board.update(boardUpdateRequestDto);
        }
        return new BoardResponseDto(board);

    }

    @Transactional
    public String deleteBoard(Long id, BoardRequestDto requestDto) throws Exception {
        Board board = findAndCheck(id);
        if (!board.getPassword().equals(requestDto.getPassword())) {
            throw new Exception("비밀번호가 일치하지 않습니다");
        } else {
            boardRepository.deleteById(id);

        }
        return board.getWriter() + "님의 글이 삭제 되었습니다.";
    }


    private Board findAndCheck(Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
    }
}
