package com.sparta.myboard.service;

import com.sparta.myboard.dto.CommentRequestDto;
import com.sparta.myboard.dto.CommentResponseDto;
import com.sparta.myboard.entity.Board;
import com.sparta.myboard.entity.Comment;
import com.sparta.myboard.entity.Member;
import com.sparta.myboard.jwt.JwtUtil;
import com.sparta.myboard.repository.BoardRepository;
import com.sparta.myboard.repository.CommentRepository;
import com.sparta.myboard.status.CustomErrorCode;
import com.sparta.myboard.status.CustomException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberService memberService;
    private final JwtUtil jwtUtil;
//    private final BoardService boardService;
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto, Long id, HttpServletRequest request) {
        String username = memberService.tokenChk(request).getUsername();
        Board boardId = findAndCheck(id);   //게시물이 있는지 탐색

        Comment comment = new Comment(requestDto, boardId); //보드의 데이터 전체 저장
        Comment save = commentRepository.save(comment);
        return new CommentResponseDto(save, username);
    }
    @Transactional
    public CommentResponseDto updateComment(CommentRequestDto requestDto, Long id, HttpServletRequest request) {
        String username = memberService.tokenChk(request).getUsername();
        Board boardId = findAndCheck(id);   //게시물이 있는지 탐색
        Comment commentId = commentRepository.findById(id).orElseThrow(
                () -> new CustomException(CustomErrorCode.NOT_FOUND_COMMENT)); //댓글 없음 에러
        if(commentId.getBoard().getMember().getUsername().equals(username)) {
            commentId.update(requestDto);
            return new CommentResponseDto(commentId, username);
        } else {
            throw new CustomException(CustomErrorCode.NOT_THE_AUTHOR);  //작성자 확인 에러메세지
        }
    }
    /*
    문제.
    1. 토큰의 여부만 확인되면 현재는 누구나 다 수정 가능.
    2. 누가 수정했는지 모름.
    */

    @Transactional
    public void deleteComment(Long id, HttpServletRequest request) {
        String username = memberService.tokenChk(request).getUsername(); //토큰의 저장된 사용자의 이름 찾기.
        Board boardId = findAndCheck(id);   //게시물이 있는지 탐색
        Comment commentId = findCommentId(id);
        if(commentId.getBoard().getMember().getUsername().equals(username)) {
            //댓글작성자의 이름이 토큰에 저장된 사용자가 맞으면 -> 삭제 진행.
            commentRepository.delete(commentId); //위 검증절차 후 댓글 삭제
        } else {
            throw new CustomException(CustomErrorCode.NOT_THE_AUTHOR);  //작성자 확인 에러메세지
        }
    }
    /*
    요구사항
    1. 토큰 검사 후 유효한 토큰인지 확인 ok
    2. 토큰의 해당 사용자가 작성한 게시글만 삭제 가능
    3. 삭제 후 Cline로 성공했다는 메세지, 상태코드 반환.
    */

    private Board findAndCheck(Long id) {   //게시글 유무 확인
        return boardRepository.findById(id).orElseThrow(
                () -> new CustomException(CustomErrorCode.NOT_FOUND_BOARD));

    }

    private Comment findCommentId (Long id) {   //댓글 유무 확인
        return commentRepository.findById(id).orElseThrow(
                () -> new CustomException(CustomErrorCode.NOT_FOUND_COMMENT));
    }

    public void tokenChkOnly(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);

        if (token == null || !jwtUtil.validateToken(token)) {
            throw new CustomException(CustomErrorCode.NOT_VALID_TOKEN); //유효하지 않은 토큰 에러
        }
    }

}
