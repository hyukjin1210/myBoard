package com.sparta.myboard.controller;

import com.sparta.myboard.dto.BoardRequestDto;
import com.sparta.myboard.entity.Board;
import com.sparta.myboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/insertBoard")
    public Board insertBoard(@RequestBody BoardRequestDto requestDto) {
        return boardService.insertBoard(requestDto);
    }

    @GetMapping("/BoardListAll")
    public List<Board> BoardListAll() {
        return boardService.getBoardListAll();
    }

    @GetMapping("/findBoardOne/")
    public Board findBoardOne (@RequestParam Long id) {
        return boardService.findBoardOne (id);
    }

    @PutMapping("/updateBoard/{id}")
    public Board updateBoard (@PathVariable Long id, @RequestBody BoardRequestDto requestDto) throws Exception {
        return boardService.updateBoard (id, requestDto);
    }

    @DeleteMapping("/deleteBoard/{id}")
     public String deleteBoard (@PathVariable Long id, @RequestBody BoardRequestDto requestDto) throws Exception {
        return boardService.deleteBoard (id, requestDto);
    }
}

