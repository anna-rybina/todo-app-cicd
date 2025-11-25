package com.github.annarybina.service;

import com.github.annarybina.model.Task;
import com.github.annarybina.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void testGetAllTasks() {
        Task task1 = new Task(1L, "Task 1", false, LocalDateTime.now());
        Task task2 = new Task(2L, "Task 2", true, LocalDateTime.now());

        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        List<Task> tasks = taskService.getAllTasks();

        assertEquals(2, tasks.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void testCreateTask() {
        Task task = new Task(null, "New Task", null, null);
        Task savedTask = new Task(1L, "New Task", false, LocalDateTime.now());

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        Task result = taskService.createTask(task);

        assertNotNull(result.getId());
        assertEquals("New Task", result.getTitle());
        assertFalse(result.getCompleted());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testUpdateTask() {
        Task existingTask = new Task(1L, "Old Task", false, LocalDateTime.now());
        Task taskDetails = new Task(null, "Updated Task", true, null);
        Task updatedTask = new Task(1L, "Updated Task", true, existingTask.getCreatedAt());

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        Optional<Task> result = taskService.updateTask(1L, taskDetails);

        assertTrue(result.isPresent());
        assertEquals("Updated Task", result.get().getTitle());
        assertTrue(result.get().getCompleted());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(existingTask);
    }

    @Test
    public void testDeleteTask() {
        when(taskRepository.existsById(1L)).thenReturn(true);

        boolean result = taskService.deleteTask(1L);

        assertTrue(result);
        verify(taskRepository, times(1)).deleteById(1L);
    }
}