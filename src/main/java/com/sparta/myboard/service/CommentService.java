package com.sparta.myboard.service;

import com.sparta.myboard.dto.CommentRequestDto;
import com.sparta.myboard.dto.CommentResponseDto;
import com.sparta.myboard.entity.UserRoleEnum;
import com.sparta.myboard.entity.Board;
import com.sparta.myboard.entity.Comment;
import com.sparta.myboard.entity.Member;
import com.sparta.myboard.repository.CommentRepository;
import com.sparta.myboard.status.CustomErrorCode;
import com.sparta.myboard.status.CustomException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final BoardService boardService;

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto,  Claims claims) {

        Board boardId = boardService.findBoard(requestDto.getBoardId());   //게시물이 있는지 확인

        Comment comment = new Comment(requestDto, boardId); //보드의 데이터 전체 저장

        Comment save = commentRepository.save(comment);

        return new CommentResponseDto(save);
    }
    @Transactional
    public CommentResponseDto updateComment(CommentRequestDto requestDto, Long id, Claims claims) {
        Member members = memberService.findMember(claims.getSubject());

        Comment commentId = findCommentId(id); //댓글 없음 에러
        if(!commentId.getBoard().getMember().getUsername().equals(members.getUsername()) || members.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new CustomException(CustomErrorCode.NOT_THE_AUTHOR);  //작성자 확인 에러메세지
        } else {
            commentId.update(requestDto);
            return new CommentResponseDto(commentId);
        }
    }

    @Transactional
    public void deleteComment(Long id, Claims claims) {
        Member members = memberService.findMember(claims.getSubject()); //토큰의 저장된 사용자 찾기.

        Comment commentId = findCommentId(id);
        if(!commentId.getBoard().getMember().getUsername().equals(members.getUsername()) || members.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new CustomException(CustomErrorCode.NOT_THE_AUTHOR);  //작성자 확인 에러메세지

        } else {
            //댓글작성자의 이름이 토큰에 저장된 사용자가 맞으면 -> 삭제 진행.
            commentRepository.delete(commentId); //위 검증절차 후 댓글 삭제
        }
//        throw 명시
    }
    /*
    요구사항
    1. 토큰 검사 후 유효한 토큰인지 확인 ok
    2. 토큰의 해당 사용자가 작성한 게시글만 삭제 가능
    3. 삭제 후 Cline로 성공했다는 메세지, 상태코드 반환.
    */

    private Comment findCommentId (Long id) {   //댓글 유무 확인
        return commentRepository.findById(id).orElseThrow(
                () -> new CustomException(CustomErrorCode.NOT_FOUND_COMMENT));
    }

}
