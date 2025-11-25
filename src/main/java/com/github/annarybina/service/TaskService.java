package com.github.annarybina.service;

import com.github.annarybina.model.Task;
import com.github.annarybina.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) {
        if (task.getCompleted() == null) {
            task.setCompleted(false);
        }
        return taskRepository.save(task);
    }

    public Optional<Task> updateTask(Long id, Task taskDetails) {
        return taskRepository.findById(id).map(existingTask -> {
            if (taskDetails.getTitle() != null) {
                existingTask.setTitle(taskDetails.getTitle());
            }
            if (taskDetails.getCompleted() != null) {
                existingTask.setCompleted(taskDetails.getCompleted());
            }
            return taskRepository.save(existingTask);
        });
    }

    public boolean deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
}