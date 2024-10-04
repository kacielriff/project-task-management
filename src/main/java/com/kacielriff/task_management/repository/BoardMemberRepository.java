package com.kacielriff.task_management.repository;

import com.kacielriff.task_management.entity.BoardMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardMemberRepository extends JpaRepository<BoardMember, Long> {
    @Query("SELECT bm FROM BoardMember bm WHERE bm.id.userId = :userId")
    Page<BoardMember> findByUserId(@Param("userId") Long userId, Pageable pageable);
}
