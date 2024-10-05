package com.kacielriff.task_management.service;

import com.kacielriff.task_management.dto.board.BoardDetailsDTO;
import com.kacielriff.task_management.entity.BoardMember;
import com.kacielriff.task_management.entity.enums.MemberRole;
import com.kacielriff.task_management.repository.BoardMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardMemberService {
    private final BoardMemberRepository boardMemberRepository;

    public BoardMember save(BoardMember boardMember) {
        return boardMemberRepository.save(boardMember);
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

    public boolean isUserMemberOfBoard(Long boardId, Long userId) {
        return boardMemberRepository.existsByBoardIdAndUserId(boardId, userId);
    }
}
