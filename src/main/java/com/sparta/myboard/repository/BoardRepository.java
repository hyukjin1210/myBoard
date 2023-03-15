package com.sparta.myboard.repository;

import com.sparta.myboard.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByCreatedAtDesc();

//    List<Board> findAllByUsername(String username);

    @Modifying
    @Query("update Board set viewCount = viewCount + 1 where id = id")
    int updateView(Long id);


}
