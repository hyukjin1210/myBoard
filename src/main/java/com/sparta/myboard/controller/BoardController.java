package com.sparta.myboard.controller;

import com.sparta.myboard.dto.BoardRequestDto;
import com.sparta.myboard.entity.Board;
import com.sparta.myboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor    //생성자 주입
@RequestMapping("/api")
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/InsertBoard/{title}/{writer}/{contents}")
    public Board InsertBoard(@PathVariable String title, @PathVariable String writer, @PathVariable String contents) {
        return boardService.InsertBoard(title, writer, contents);
    }

//    @PostMapping("/api/InsertBoard")
//    public Board InsertBoard(@RequestBody BoardRequestDto requestDto) {
//        return boardService.InsertBoard(requestDto);
//    }

    @GetMapping("/BoardListAll")
    public List<Board> BoardListAll() {
        return boardService.getBoardListAll();
    }

    @GetMapping("/findBoardOne/{id}")
    public Board findBoardOne (@PathVariable Long id) {

        return boardService.findBoardOne (id);
    }
}
