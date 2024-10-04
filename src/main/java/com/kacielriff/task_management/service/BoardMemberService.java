package com.kacielriff.task_management.service;

import com.kacielriff.task_management.entity.BoardMember;
import com.kacielriff.task_management.repository.BoardMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardMemberService {
    private final BoardMemberRepository boardMemberRepository;

    public Page<BoardMember> getUserBoards(Long userId, Pageable pageable) {
        return boardMemberRepository.findByUserId(userId, pageable);
    }
}
