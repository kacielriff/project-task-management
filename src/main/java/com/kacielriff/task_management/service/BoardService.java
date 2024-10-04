package com.kacielriff.task_management.service;

import com.kacielriff.task_management.dto.board.SimpleBoardDTO;
import com.kacielriff.task_management.dto.shared.PageDTO;
import com.kacielriff.task_management.entity.Board;
import com.kacielriff.task_management.entity.BoardMember;
import com.kacielriff.task_management.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserService userService;
    private final BoardMemberService boardMemberService;

    public PageDTO<SimpleBoardDTO> listUserBoards(Integer page, Integer size) throws Exception {
        Long userId = userService.getIdLoggedUser();

        Pageable pageable = PageRequest.of(page, size, Sort.by("created_at"));

        Page<BoardMember> boards = boardMemberService.getUserBoards(userId, pageable);

        if (boards.isEmpty()) return emptyPage(pageable);

        return new PageDTO<>(
                boards.getTotalElements(),
                boards.getTotalPages(),
                boards.getPageable().getPageNumber(),
                boards.getSize(),
                boards.getContent()
                        .stream()
                        .map(this::getSimpleBoardDTO)
                        .collect(Collectors.toList())
        );
    }

    private <T> PageDTO<T> emptyPage(Pageable pageable) {
        return new PageDTO<>(0L, 0, 0, pageable.getPageSize(), new ArrayList<>());
    }

    private SimpleBoardDTO getSimpleBoardDTO(BoardMember bm) {
        return new SimpleBoardDTO(
                bm.getBoard().getId(),
                bm.getBoard().getName(),
                bm.getRole(),
                bm.getBoard().getCreatedAt());
    }
}
