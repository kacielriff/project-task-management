package com.kacielriff.task_management.service;

import com.kacielriff.task_management.dto.board.BoardDetailsDTO;
import com.kacielriff.task_management.entity.BoardMember;
import com.kacielriff.task_management.entity.enums.MemberRole;
import com.kacielriff.task_management.exception.NotFoundException;
import com.kacielriff.task_management.repository.BoardMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardMemberService {
    private final BoardMemberRepository boardMemberRepository;

    public BoardMember save(BoardMember boardMember) {
        return boardMemberRepository.save(boardMember);
    }

    public BoardMember getById(Long boardId) throws Exception {
        return boardMemberRepository.findByBoardId(boardId)
                .orElseThrow(() -> new NotFoundException("Board não encontrado."));
    }

    public BoardMember getWhereUserIsOwner(Long boardId) throws Exception {
        return boardMemberRepository.findOwnerByBoardId(boardId, MemberRole.OWNER)
                .orElseThrow(() -> new NotFoundException("Board não encontrado."));
    }

    public Page<BoardMember> getUserBoards(Long userId, Pageable pageable) {
        return boardMemberRepository.findByUserId(userId, pageable);
    }

    public List<BoardMember> getUserBoardsWithRole(Long userId, MemberRole role) {
        return boardMemberRepository.findByUserIdAndRole(userId, role);
    }

    public List<BoardMember> getBoardWithMembers(Long boardId) throws Exception {
        return boardMemberRepository.findMembersByBoardId(boardId);
    }

    public boolean isUserOwnerOfBoard(Long boardId, Long userId) {
        return boardMemberRepository.findOwnerByBoardId(boardId, MemberRole.OWNER).isPresent();
    }

    public boolean isUserMemberOfBoard(Long boardId, Long userId) {
        return boardMemberRepository.existsByBoardIdAndUserId(boardId, userId);
    }
}
