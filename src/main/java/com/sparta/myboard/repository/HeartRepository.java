package com.sparta.myboard.repository;

import com.sparta.myboard.entity.Board;
import com.sparta.myboard.entity.Heart;
import com.sparta.myboard.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Optional<Heart> findByMemberAndBoard(Member member, Board board);

    int countByBoardId(Long boardId);   //하트 증가
}
