package com.kacielriff.task_management.repository;

import com.kacielriff.task_management.entity.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList, Long> {
    boolean existsByName(String name);

    boolean existsByPosition(Integer position);

    @Query("DELETE FROM TaskList tl WHERE tl.id = :id")
    void deleteAllById(@Param("id") Long id);
}
