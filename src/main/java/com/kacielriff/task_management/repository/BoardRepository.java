package com.kacielriff.task_management.repository;

import com.kacielriff.task_management.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    boolean existsById(Long id);

    @Modifying
    @Query("DELETE FROM Board b WHERE b.id = :id")
    void deleteAllById(@Param("id") Long id);
}
