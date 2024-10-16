package com.kacielriff.task_management.repository;

import com.kacielriff.task_management.entity.BoardMember;
import com.kacielriff.task_management.entity.enums.MemberRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardMemberRepository extends JpaRepository<BoardMember, Long> {
    @Query("SELECT bm FROM BoardMember bm WHERE bm.id.boardId = :boardId")
    Optional<BoardMember> findByBoardId(@Param("boardId") Long boardId);

    @Query("SELECT bm FROM BoardMember bm WHERE bm.id.userId = :userId ORDER BY bm.board.createdAt ASC")
    Page<BoardMember> findByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT bm FROM BoardMember bm WHERE bm.id.userId = :userId AND bm.role = :role ORDER BY bm.board.createdAt ASC")
    List<BoardMember> findByUserIdAndRole(@Param("userId") Long userId, @Param("role") MemberRole role);

    @Query("SELECT bm FROM BoardMember bm WHERE bm.board.id = :boardId")
    List<BoardMember> findMembersByBoardId(@Param("boardId") Long boardId);

    @Query("SELECT bm FROM BoardMember bm WHERE bm.board.id = :boardId AND bm.role = :role")
    Optional<BoardMember> findOwnerByBoardId(@Param("boardId") Long boardId, @Param("role") MemberRole role);

    boolean existsByBoardIdAndUserId(Long boardId, Long userId);
}
