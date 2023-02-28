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

//    @PostMapping("/InsertBoard/{title}/{writer}/{password}/{contents}")
//    public Board InsertBoard(@PathVariable String title, @PathVariable String writer, @PathVariable String password, @PathVariable String contents) {
//        return boardService.InsertBoard(title, writer, password, contents);
//    }

    @PostMapping("/insertBoard")
    public Board insertBoard(@RequestBody BoardRequestDto requestDto) {
        return boardService.insertBoard(requestDto);
    }

    @GetMapping("/BoardListAll")
    public List<Board> BoardListAll() {
        return boardService.getBoardListAll();
    }

    @GetMapping("/findBoardOne/{id}")
    public Board findBoardOne (@PathVariable Long id) {

        return boardService.findBoardOne (id);
    }

    @PutMapping("/updateBoard/{id}")
    public Long updateBoard (@RequestBody BoardRequestDto requestDto, @PathVariable Long id) throws Exception {
        return boardService.updateBoard (id, requestDto);
    }

//    @PutMapping("/updateBoard/{password}")
//    public String updateBoard (@PathVariable String password, @RequestBody BoardRequestDto requestDto) {
//        return boardService.updateBoard (password, requestDto);
//    }

    @DeleteMapping("/deleteBoard/{id}/{password}")
     public String deleteBoard (@PathVariable Long id, @PathVariable String password) throws Exception {

        return boardService.deleteBoard (id, password);
    }
}
// http://localhost:8080/api/InsertBoard/{title}/{writer}/{password}/{contents}