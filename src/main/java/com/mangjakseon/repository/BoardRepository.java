package com.mangjakseon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import com.mangjakseon.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Integer>, QuerydslPredicateExecutor<Board> {
    // 조회수
    @Modifying
    @Query("update Board b set b.b_count = b.b_count + 1 where b.bno = :bno")
    int updateView(Integer bno);
}
