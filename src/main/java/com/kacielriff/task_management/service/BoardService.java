package com.kacielriff.task_management.service;

import com.kacielriff.task_management.dto.board.BoardDetailsDTO;
import com.kacielriff.task_management.dto.board.CreateBoardDTO;
import com.kacielriff.task_management.dto.board.SimpleBoardDTO;
import com.kacielriff.task_management.dto.member.MemberWithRoleDTO;
import com.kacielriff.task_management.dto.member.SimpleMemberDTO;
import com.kacielriff.task_management.dto.shared.PageDTO;
import com.kacielriff.task_management.dto.user.LoggedUserDTO;
import com.kacielriff.task_management.entity.Board;
import com.kacielriff.task_management.entity.BoardMember;
import com.kacielriff.task_management.entity.User;
import com.kacielriff.task_management.entity.enums.MemberRole;
import com.kacielriff.task_management.entity.pk.BoardMemberPK;
import com.kacielriff.task_management.exception.ConflictException;
import com.kacielriff.task_management.exception.NotFoundException;
import com.kacielriff.task_management.exception.UnauthorizedException;
import com.kacielriff.task_management.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserService userService;
    private final BoardMemberService boardMemberService;

    public PageDTO<SimpleBoardDTO> listUserBoards(Integer page, Integer size) {
        Long userId = userService.getIdLoggedUser();
        Pageable pageable = PageRequest.of(page, size);

        Page<BoardMember> boards = boardMemberService.getUserBoards(userId, pageable);
        if (boards.isEmpty()) return emptyPage(pageable);

        List<SimpleBoardDTO> simpleBoardDTOs = boards.getContent().stream()
                .map(this::convertToSimpleBoardDTO)
                .collect(Collectors.toList());

        return createPageDTO(boards, simpleBoardDTOs);
    }

    public BoardDetailsDTO listBoardWithMembers(Long boardId) throws Exception {
        if (!boardRepository.existsById(boardId)) {
            throw new NotFoundException("Board não encontrado.");
        }

        if (!boardMemberService.isUserMemberOfBoard(boardId, userService.getIdLoggedUser())) {
            throw new UnauthorizedException("Usuário não faz parte desse board.");
        }

        List<BoardMember> boardMembers = boardMemberService.getBoardWithMembers(boardId);

        // filtra o owner
        SimpleMemberDTO owner = boardMembers.stream()
                .filter(bm -> bm.getRole() == MemberRole.OWNER)
                .map(bm -> new SimpleMemberDTO(bm.getUser().getId(), bm.getUser().getName()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Owner não encontrado."));

        // filtra os outros membros (exceto owner)
        List<MemberWithRoleDTO> members = boardMembers.stream()
                .filter(bm -> bm.getRole() != MemberRole.OWNER)
                .map(bm -> new MemberWithRoleDTO(bm.getUser().getId(), bm.getUser().getName(), bm.getRole()))
                .collect(Collectors.toList());

        return convertToBoardDetailsDTO(boardMembers.get(0), members);
    }

    public BoardDetailsDTO create(CreateBoardDTO createBoardDTO) throws Exception {
        Long userId = userService.getIdLoggedUser();

        if (userAlreadyOwnsBoard(userId, createBoardDTO.getName())) {
            throw new ConflictException("Usuário já possui um board com este nome.");
        }

        Board newBoard = saveNewBoard(createBoardDTO.getName());
        BoardMember savedBoardMember = addOwnerToBoard(newBoard, userId);

        return convertToBoardDetailsDTO(savedBoardMember, Collections.emptyList());
    }

    private <T> PageDTO<T> emptyPage(Pageable pageable) {
        return new PageDTO<>(0L, 0, 0, pageable.getPageSize(), new ArrayList<>());
    }

    private <T> PageDTO<T> createPageDTO(Page<BoardMember> boards, List<T> content) {
        return new PageDTO<>(
                boards.getTotalElements(),
                boards.getTotalPages(),
                boards.getNumber(),
                boards.getSize(),
                content
        );
    }

    private boolean userAlreadyOwnsBoard(Long userId, String boardName) {
        List<BoardMember> ownedBoards = boardMemberService.getUserBoardsWithRole(userId, MemberRole.OWNER);
        return ownedBoards.stream().anyMatch(bm -> bm.getBoard().getName().equals(boardName));
    }

    private Board saveNewBoard(String boardName) {
        Board board = new Board();
        board.setName(boardName);
        return boardRepository.save(board);
    }

    private BoardMember addOwnerToBoard(Board board, Long userId) throws Exception {
        User user = new User();
        user.setId(userId);

        BoardMemberPK boardMemberPK = new BoardMemberPK();
        boardMemberPK.setBoardId(board.getId());
        boardMemberPK.setUserId(userId);

        BoardMember boardMember = new BoardMember();
        boardMember.setId(boardMemberPK);
        boardMember.setBoard(board);
        boardMember.setUser(user);
        boardMember.setRole(MemberRole.OWNER);

        return boardMemberService.save(boardMember);
    }

    private SimpleBoardDTO convertToSimpleBoardDTO(BoardMember bm) {
        return new SimpleBoardDTO(
                bm.getBoard().getId(),
                bm.getBoard().getName(),
                bm.getRole(),
                bm.getBoard().getCreatedAt()
        );
    }

    private BoardDetailsDTO convertToBoardDetailsDTO(BoardMember bm, List<MemberWithRoleDTO> members) {
        return new BoardDetailsDTO(
                bm.getBoard().getId(),
                bm.getBoard().getName(),
                new SimpleMemberDTO(bm.getUser().getId(), bm.getUser().getName()),
                members,
                bm.getBoard().getCreatedAt()
        );
    }
}
