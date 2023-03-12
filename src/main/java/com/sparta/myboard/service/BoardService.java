package com.sparta.myboard.service;

import com.sparta.myboard.dto.BoardRequestDto;
import com.sparta.myboard.dto.BoardResponseDto;
import com.sparta.myboard.dto.BoardUpdateRequestDto;
import com.sparta.myboard.entity.UserRoleEnum;
import com.sparta.myboard.entity.Board;
import com.sparta.myboard.entity.Member;
import com.sparta.myboard.repository.BoardRepository;
import com.sparta.myboard.status.CustomErrorCode;
import com.sparta.myboard.status.CustomException;
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
    private final MemberService memberService;

    @Transactional  //조인컬럼을 사용하는 경우
    public BoardResponseDto createBoard(BoardRequestDto requestDto, HttpServletRequest request) {
        Member members = memberService.tokenChk(request);

        Board board = new Board(requestDto, members);   //ResponseDto에 해답이 있다.
        Board save = boardRepository.saveAndFlush(board);

        return new BoardResponseDto(save);
    }

//    public BoardResponseDto createBoard(BoardRequestDto requestDto, Claims claims) {
//
//        Board board = new Board(requestDto, claims);
//        /*
//        이렇게 claims를 넘기려면 몇가지 수정사항이 있다.
//        1. Board엔티티에서의 Board생성자 수정
//        2. Board생성자를 수정함에 따라 연관관계를 맺어둔 Member와의 관계에 대해서 다시 생각해봐야 함.
//        만약 멤버와의 연관관계를 끊어주고 이런 형태로 사용이 가능하다면,
//        토큰에 대한 검증은 컨트롤러에서 끝내고 서비스로직은 조금 더 책임이 낮아진다.
//
//        */
//        Board save = boardRepository.saveAndFlush(board);
//
//        return new BoardResponseDto(save);
//    }

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

//        String username = memberService.tokenChk(request).getUsername();
        Member members = memberService.tokenChk(request);

//        if (board.getMember().getUsername().equals(username) || board.getMember().getAuth().equals(AuthEnum.ADMIN)) {
        if (!board.getMember().getUsername().equals(members.getUsername()) || members.getRole().equals(UserRoleEnum.ADMIN)) {
            //게시물에 저장된 유저네임 = 토큰에 저장되어있는 유저네임 또는 토큰에 저장되어있는 유저의 권한 = admin인 경우
            board.update(boardUpdateRequestDto);
            throw new CustomException(CustomErrorCode.REQUIRED_LOGIN);

        }
        return new BoardResponseDto(board);
    }

    @Transactional
    public void deleteBoard(Long id, HttpServletRequest request) {
        Board board = findAndCheck(id);

        Member members = memberService.tokenChk(request);

        if (!board.getMember().getUsername().equals(members.getUsername()) || members.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new CustomException(CustomErrorCode.REQUIRED_LOGIN);
        }
        boardRepository.deleteById(id);
    }

    public Board findAndCheck(Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new CustomException(CustomErrorCode.NOT_FOUND_BOARD));
    }

}
