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

//    @Transactional
//    public Board InsertBoard(String title, String writer, String password, String contents) {
//        BoardRequestDto requestDto = new BoardRequestDto(title, writer, password, contents);
//        Board board = new Board(requestDto);
//        boardRepository.save(board);
//        return board;
//    }
    @Transactional
    public Board insertBoard (BoardRequestDto requestDto) {
        Board board = new Board(requestDto);
        boardRepository.save(board);
        return board;
    }

    @Transactional(readOnly = true)
    public List<Board> getBoardListAll() {
        return boardRepository.findAllByOrderByCreateAtDesc();
    }

    @Transactional
    public Board findBoardOne(Long id) {

        return boardRepository.findById(id).orElse(null);
    }

    @Transactional
    public Long updateBoard (Long id , BoardRequestDto requestDto) throws Exception {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다."));
//        if(board.getPassword().equals(requestDto.getPassword())) {
//            requestDto.setTitle(requestDto.getTitle());
//            requestDto.setWriter(requestDto.getWriter());
//            requestDto.setContents(requestDto.getContents());
//        }
        if(!board.getPassword().equals(requestDto.getPassword())) {
            throw new Exception("비밀번호가 일치하지 않습니다");
        }
        board.update(requestDto);
        return board.getId();

    }

//    @Transactional
//    public Long updateBoard (Long id, String password, BoardRequestDto requestDto) {
//        Board board = boardRepository.findById(id).orElseThrow(() ->
//                new NullPointerException("해당 아이디가 존재하지 않습니다."));
//        if(password.equals(board.getPassword())) {
//            board.setId(board.getId());
//            board.setTitle(requestDto.getTitle());
//            board.setWriter(requestDto.getWriter());
//            board.setPassword(board.getPassword());
//            board.setContents(requestDto.getContents());
//        }
//        board.update(requestDto);
//        return id;
//    }

//    @Transactional
//    public String updateBoard (String password, BoardRequestDto requestDto) {
//        Board board = boardRepository.findByPassword(password).;
//        board.updateBoard(requestDto);
//        return null;
//    }
    /*
    데이터 삭제부분
    비밀번호를 url로 전달하지 않아도 비교가 가능해야 함.
    해당 게시글 비밀번호의 비교를 그러면 어떻게 할 것인가?
    * */
    @Transactional
    public String deleteBoard(Long id, String password) throws Exception {  //리턴을 받을 필요가 있을까..?
        String msg = "";
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다."));
        if(!board.getPassword().equals(password)) {
            throw new Exception("비밀번호가 일치하지 않습니다");
        } else {
            boardRepository.deleteById(id);
            msg = "성공입니다.";
        }
        return msg;
    }
}
