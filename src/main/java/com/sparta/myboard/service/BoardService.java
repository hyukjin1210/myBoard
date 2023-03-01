package com.sparta.myboard.service;

import com.sparta.myboard.dto.BoardRequestDto;
import com.sparta.myboard.entity.Board;
import com.sparta.myboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public Board insertBoard(BoardRequestDto requestDto) {
        Board board = new Board(requestDto);
        return boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public List<Board> getBoardListAll() {
        return boardRepository.findAllByOrderByCreateAtDesc();
    }

    @Transactional
    public Board findBoardOne(Long id) {
        return findAndCheck(id);
    }

    @Transactional
    public Board updateBoard(Long id, BoardRequestDto requestDto) throws Exception {
        Board board = findAndCheck(id);
        if (!board.getPassword().equals(requestDto.getPassword())) {
            throw new Exception("비밀번호가 일치하지 않습니다");
        } else {
            board.update(requestDto);
        }
        return board;

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
