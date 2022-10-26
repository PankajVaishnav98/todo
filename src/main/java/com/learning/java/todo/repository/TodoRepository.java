package com.learning.java.todo.repository;

import com.learning.java.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    Optional<List<Todo>> findByUserId(Long userId);
    Todo findByUserIdAndTitle(Long userId,String title);
}
