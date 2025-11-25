package com.github.annarybina.repository;

import com.github.annarybina.model.Task;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TaskRepository {
    private final Map<Long, Task> tasks = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    public Task save(Task task) {
        if (task.getId() == null) {
            task.setId(idCounter.getAndIncrement());
            task.setCreatedAt(LocalDateTime.now());
        }
        tasks.put(task.getId(), task);
        return task;
    }

    public void deleteById(Long id) {
        tasks.remove(id);
    }

    public boolean existsById(Long id) {
        return tasks.containsKey(id);
    }
}