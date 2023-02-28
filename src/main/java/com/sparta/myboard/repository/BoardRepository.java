package com.sparta.myboard.repository;

import com.sparta.myboard.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {   //데이터베이스 연결!
    List<Board> findAllByOrderByCreateAtDesc(); //전체 리스트 조회


}
