package com.sparta.myboard.controller;

import com.sparta.myboard.dto.BoardRequestDto;
import com.sparta.myboard.dto.BoardUpdateRequestDto;
import com.sparta.myboard.dto.BoardCreateResponseDto;
import com.sparta.myboard.entity.Board;
import com.sparta.myboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
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

//    @PostMapping("/insertBoard")
//    public String insertBoard(@Valid @RequestBody BoardRequestDto requestDto, BindingResult result) {
//        if (result.hasErrors()) {
//            return result.getFieldErrors().toString();
//        } else {
//            boardService.insertBoard(requestDto);
//            return "유효성 검증에서 통과하였습니다";
//        }
//    }
    @PostMapping("/insertBoard")
    public BoardCreateResponseDto insertBoard(@Valid @RequestBody BoardRequestDto requestDto) {
            return  boardService.insertBoard(requestDto);
        }

    @GetMapping("/BoardListAll")
    public List<BoardCreateResponseDto> BoardListAll() {
        return boardService.getBoardListAll();
    }

    @GetMapping("/findBoardOne/")
    public BoardCreateResponseDto findBoardOne(@RequestParam Long id) {
        return boardService.findBoardOne(id);
    }

    @PutMapping("/updateBoard/{id}")
    public String updateBoard(@PathVariable Long id,
                              @Validated @RequestBody BoardUpdateRequestDto boardUpdateRequestDto,
                              BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return result.getFieldErrors().toString();
        } else {
            boardService.updateBoard(id, boardUpdateRequestDto);
            return "정보 수정이 완료 되었습니다.";
        }
    }

    @DeleteMapping("/deleteBoard/{id}")
    public String deleteBoard(@PathVariable Long id,
                              @RequestBody BoardRequestDto requestDto) throws Exception {
        return boardService.deleteBoard(id, requestDto);
    }

}

