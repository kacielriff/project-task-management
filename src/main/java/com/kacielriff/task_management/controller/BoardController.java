package com.kacielriff.task_management.controller;

import com.kacielriff.task_management.dto.board.BoardDetailsDTO;
import com.kacielriff.task_management.dto.board.CreateBoardDTO;
import com.kacielriff.task_management.dto.board.SimpleBoardDTO;
import com.kacielriff.task_management.dto.shared.MessageDTO;
import com.kacielriff.task_management.dto.shared.PageDTO;
import com.kacielriff.task_management.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("/board")
@Validated
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<BoardDetailsDTO> listById(
            @RequestParam(value = "id", required = true) @Positive Long boardId) throws Exception {
        return new ResponseEntity<>(boardService.listBoardWithMembers(boardId), HttpStatus.OK);
    }

    @GetMapping("/list-user-boards")
    public ResponseEntity<PageDTO<SimpleBoardDTO>> listUserBoards(
            @RequestParam(value = "page", required = false, defaultValue = "0") @PositiveOrZero Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") @Positive Integer size) {
        return new ResponseEntity<>(boardService.listUserBoards(page, size), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BoardDetailsDTO> create(
            @RequestBody @Valid CreateBoardDTO createBoardDTO) throws Exception {
        return new ResponseEntity<>(boardService.create(createBoardDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<BoardDetailsDTO> edit(
            @RequestParam(value = "id", required = true) @Positive Long boardId,
            @RequestBody @Valid CreateBoardDTO createBoardDTO) throws Exception {
        return new ResponseEntity<>(boardService.edit(boardId, createBoardDTO), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<MessageDTO> delete(
            @RequestParam(value = "id", required = true) @Positive Long boardId) throws Exception {
        return new ResponseEntity<>(boardService.delete(boardId), HttpStatus.OK);
    }
}
