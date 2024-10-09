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
    private final CardService cardService;

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

        Integer oldPosition = list.getPosition();
        Integer newPosition = createListDTO.getPosition();

        if (Objects.equals(oldPosition, newPosition)) {
            list.setName(createListDTO.getName());
            taskListRepository.save(list);
        } else {
            List<TaskList> lists = taskListRepository.findAll();

            if (newPosition > oldPosition) {
                lists.stream()
                        .filter(l -> !Objects.equals(l.getId(), list.getId()))
                        .filter(l -> l.getPosition() > oldPosition && l.getPosition() <= newPosition)
                        .forEach(l -> l.setPosition(l.getPosition() - 1));
            } else {
                lists.stream()
                        .filter(l -> l.getPosition() < oldPosition && l.getPosition() >= newPosition)
                        .forEach(l -> l.setPosition(l.getPosition() + 1));
            }

            list.setName(createListDTO.getName());
            list.setPosition(newPosition);
            taskListRepository.save(list);

            taskListRepository.saveAll(lists);
        }

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

        if (cardService.doesListHaveCards(listId)) {
            throw new ConflictException("A lista não pode ser excluída porque ainda possui cards.");
        }

        taskListRepository.deleteAllById(listId);

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
