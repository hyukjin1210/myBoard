package com.sparta.myboard.repository;

import com.sparta.myboard.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByCreatedAtDesc();

//    List<Board> findAllByUsername(String username);


}
