package com.kacielriff.task_management.repository;

import com.kacielriff.task_management.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    boolean existsByListId(Long listId);
}
