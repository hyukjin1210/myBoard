package com.sparta.myboard.controller;

import com.sparta.myboard.dto.BoardRequestDto;
import com.sparta.myboard.dto.BoardResponseDto;
import com.sparta.myboard.dto.BoardUpdateRequestDto;
import com.sparta.myboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/insertBoard")
    public BoardResponseDto insertBoard(@Valid @RequestBody BoardRequestDto requestDto) {
        return boardService.insertBoard(requestDto);
    }

    @GetMapping("/BoardListAll")
    public List<BoardResponseDto> BoardListAll() {
        return boardService.getBoardListAll();
    }

    @GetMapping("/findBoardOne/")
    public BoardResponseDto findBoardOne(@RequestParam Long id) {
        return boardService.findBoardOne(id);
    }

    @PutMapping("/updateBoard/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id,
                                        @Validated @RequestBody BoardUpdateRequestDto boardUpdateRequestDto) throws Exception {
        return boardService.updateBoard(id, boardUpdateRequestDto);
    }

    @DeleteMapping("/deleteBoard/{id}")
    public String deleteBoard(@PathVariable Long id,
                              @RequestBody BoardRequestDto requestDto) throws Exception {
        return boardService.deleteBoard(id, requestDto);
    }

}

