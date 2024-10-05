package com.kacielriff.task_management.repository;

import com.kacielriff.task_management.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    boolean existsById(Long id);
}
