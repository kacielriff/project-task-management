package com.kacielriff.task_management.service;

import com.kacielriff.task_management.dto.list.CreateListDTO;
import com.kacielriff.task_management.dto.list.ListDetailsDTO;
import com.kacielriff.task_management.dto.list.SimpleListDTO;
import com.kacielriff.task_management.dto.shared.MessageDTO;
import com.kacielriff.task_management.entity.Board;
import com.kacielriff.task_management.entity.TaskList;
import com.kacielriff.task_management.exception.ConflictException;
import com.kacielriff.task_management.exception.NotFoundException;
import com.kacielriff.task_management.exception.UnauthorizedException;
import com.kacielriff.task_management.repository.TaskListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskListService {
    private final TaskListRepository taskListRepository;
    private final UserService userService;
    private final BoardService boardService;
    private final BoardMemberService boardMemberService;

    public List<SimpleListDTO> list() {
        List<TaskList> lists = taskListRepository.findAll();

        if (lists.isEmpty()) {
            return Collections.emptyList();
        }

        return lists.stream()
                .map(this::convertToSimpleListDTO)
                .collect(Collectors.toList());
    }

    public List<ListDetailsDTO> create(CreateListDTO createListDTO) throws Exception {
        if (!boardMemberService.isUserOwnerOfBoard(createListDTO.getBoardId(), userService.getIdLoggedUser())) {
            throw new UnauthorizedException("Usuário não é dono deste board.");
        }

        if (!taskListRepository.existsByName(createListDTO.getName())) {
            throw new ConflictException("Já existe uma lista com este nome.");
        }

        List<TaskList> lists;

        if (taskListRepository.existsByPosition(createListDTO.getPosition())) {
            lists = taskListRepository.findAll()
                    .stream()
                    .map(list -> {
                        if (list.getPosition() >= createListDTO.getPosition()) {
                            list.setPosition(list.getPosition() + 1);
                        }
                        return list;
                    })
                    .collect(Collectors.toList());

            taskListRepository.saveAll(lists);
        }

        save(createListDTO);

        lists = taskListRepository.findAll();

        return lists.stream()
                .map(this::convertToListDetailsDTO)
                .collect(Collectors.toList());
    }

    public List<ListDetailsDTO> edit(Long listId, CreateListDTO createListDTO) throws Exception {
        if (!boardMemberService.isUserOwnerOfBoard(createListDTO.getBoardId(), userService.getIdLoggedUser())) {
            throw new UnauthorizedException("Usuário não é dono deste board.");
        }

        if (!taskListRepository.existsByName(createListDTO.getName())) {
            throw new ConflictException("Já existe uma lista com este nome.");
        }

        TaskList list = taskListRepository.findById(listId)
                .orElseThrow(() -> new NotFoundException("Lista não encontrada."));

        if (Objects.equals(list.getPosition(), createListDTO.getPosition())) {
            list.setName(createListDTO.getName());
            taskListRepository.save(list);
        }

        // TODO:
        //      fazer parte que altera a posicao das listas
        //      levar em conta que a lista pode ser movida para frente
        //      e para trás

        return taskListRepository.findAll()
                .stream()
                .map(this::convertToListDetailsDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public MessageDTO delete(Long listId) throws Exception {
        TaskList list = taskListRepository.findById(listId)
                .orElseThrow(() -> new NotFoundException("Lista não encontrada."));

        if (!boardMemberService.isUserOwnerOfBoard(list.getBoard().getId(), userService.getIdLoggedUser())) {
            throw new UnauthorizedException("Usuário não é dono deste board.");
        }

        taskListRepository.deleteAllById(listId);

        // TODO:
        //      fazer o delete em cascata dos cards ou alterar a lógica
        //      para não permitir a deleção de uma lista que contenha cards
        //      (segunda opção é mais segura e intuitiva para evitar erros humanos)

        return new MessageDTO("Lista excluída com sucesso.");
    }

    private TaskList save(CreateListDTO list) throws Exception {
        Board board = boardService.getById(list.getBoardId());

        TaskList taskList = new TaskList();

        taskList.setName(list.getName());
        taskList.setBoard(board);
        taskList.setPosition(list.getPosition());

        return taskListRepository.save(taskList);
    }

    private SimpleListDTO convertToSimpleListDTO(TaskList list) {
        return new SimpleListDTO(
                list.getId(),
                list.getName(),
                list.getPosition()
        );
    }

    private ListDetailsDTO convertToListDetailsDTO(TaskList list) {
        return new ListDetailsDTO(
                list.getId(),
                list.getName(),
                list.getPosition()
                // List<Card>
        );
    }
}
