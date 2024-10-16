package com.kacielriff.task_management.controller;

import com.kacielriff.task_management.dto.list.CreateListDTO;
import com.kacielriff.task_management.dto.list.ListDetailsDTO;
import com.kacielriff.task_management.dto.list.SimpleListDTO;
import com.kacielriff.task_management.dto.shared.MessageDTO;
import com.kacielriff.task_management.service.TaskListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/list")
@Validated
@RequiredArgsConstructor
public class TaskListController {
    private final TaskListService taskListService;

    @GetMapping
    public ResponseEntity<List<SimpleListDTO>> list() {
        return new ResponseEntity<>(taskListService.list(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<List<ListDetailsDTO>> create(
            @RequestBody @Valid CreateListDTO createListDTO) throws Exception {
        return new ResponseEntity<>(taskListService.create(createListDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{listId}")
    public ResponseEntity<List<ListDetailsDTO>> edit(
            @PathVariable("listId") @Positive Long listId,
            @RequestBody @Valid CreateListDTO createListDTO) throws Exception {
        return new ResponseEntity<>(taskListService.edit(listId, createListDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<MessageDTO> delete(
            @PathVariable("listId") @Positive Long listId) throws Exception {
        return new ResponseEntity<>(taskListService.delete(listId), HttpStatus.OK);
    }
}
