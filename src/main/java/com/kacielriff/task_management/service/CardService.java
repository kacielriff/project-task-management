package com.kacielriff.task_management.service;

import com.kacielriff.task_management.exception.UnauthorizedException;
import com.kacielriff.task_management.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final UserService userService;
    private final BoardMemberService boardMemberService;

    public boolean doesListHaveCards(Long listId) {
        return cardRepository.existsByListId(listId);
    }
}
