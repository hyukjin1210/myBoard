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

//    private final  BoardRequestDto requestDto;


//    public BoardService(BoardRepository boardRepository) {
//        this.boardRepository = boardRepository;
//    }

    @Transactional
    public Board InsertBoard(String title, String writer, String contents) {
        BoardRequestDto requestDto = new BoardRequestDto(title, writer, contents);
        Board board = new Board(requestDto);
        boardRepository.save(board);
        return board;
    }

    @Transactional(readOnly = true)
    public List<Board> getBoardListAll() {
        return boardRepository.findAllByOrderByCreateAtDesc();
    }

    @Transactional
    public Board findBoardOne (Long id) {

        return boardRepository.findById(id).orElse(null);
    }
}
