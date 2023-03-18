package com.sparta.myboard.service;

import com.sparta.myboard.dto.HeartRequestDto;
import com.sparta.myboard.entity.Board;
import com.sparta.myboard.entity.Heart;
import com.sparta.myboard.entity.Member;
import com.sparta.myboard.repository.HeartRepository;
import com.sparta.myboard.security.MemberDetailsImpl;
import com.sparta.myboard.status.CustomErrorCode;
import com.sparta.myboard.status.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Lazy
public class HeartService {

    private final BoardService boardService;
    private final MemberService memberService;
    private final HeartRepository heartRepository;

    @Transactional
    public boolean addHeart (Member member, Long boardId) {
        Board board = boardService.findBoard(boardId);
        if (duplicateHeart(member, board)) {
            heartRepository.save(new Heart(member, board));
        }
        return true;
    }

    // 사용자가 이미 좋아요 한 게시물인지 확인
    private boolean duplicateHeart(Member member, Board board) {
        return heartRepository.findByMemberAndBoard(member, board).isEmpty();
    }


//    @Transactional
//    public void likeBoard(HeartRequestDto requestDto, MemberDetailsImpl memberDetails) {
//        Member memberId = memberService.findMemberId(requestDto.getMemberId());
//
//        Board boardId = boardService.findBoard(requestDto.getBoardId());
//
//        //좋아요가 되어있는지 확인
//        if(heartRepository.findByMemberAndBoard(memberId, boardId).isPresent()) {
//            throw new CustomException(CustomErrorCode.DUPLICATE_LIKE);
//        }
//
//        if (memberDetails.getMember().getId() == null) {
//            throw new CustomException(CustomErrorCode.NOT_FOUND_MEMBER);
//        }
//        heartRepository.save(new Heart(memberId, boardId));
//
//    }

    @Transactional
    public void disLikeBoard(HeartRequestDto requestDto, MemberDetailsImpl memberDetails) {
        Member memberId = memberService.findMemberId(requestDto.getMemberId());

        Board boardId = boardService.findBoard(requestDto.getBoardId());

        Heart heart = heartRepository.findByMemberAndBoard(memberId, boardId).orElseThrow(
                () -> new CustomException(CustomErrorCode.NOT_FOUND_HEART));

//        삭제조건 다시 만들어야 함.
//        if (!memberId.equals(memberDetails.getMember().getId())) {
//            throw new CustomException(CustomErrorCode.NOT_VALID_TOKEN);
//        }

        heartRepository.delete(heart);
    }

}
