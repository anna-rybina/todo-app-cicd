package com.github.annarybina.controller;

import com.github.annarybina.model.Task;
import com.github.annarybina.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllTasks() throws Exception {
        // Given
        Task task1 = new Task(1L, "Task 1", false, LocalDateTime.now());
        Task task2 = new Task(2L, "Task 2", true, LocalDateTime.now());
        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskService.getAllTasks()).thenReturn(tasks);

        // When & Then
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetTaskById() throws Exception {
        // Given
        Task task = new Task(1L, "Test Task", false, LocalDateTime.now());
        when(taskService.getTaskById(1L)).thenReturn(Optional.of(task));

        // When & Then
        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTaskById_NotFound() throws Exception {
        // Given
        when(taskService.getTaskById(1L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateTask() throws Exception {
        // Given
        Task taskToCreate = new Task();
        taskToCreate.setTitle("New Task");

        Task savedTask = new Task(1L, "New Task", false, LocalDateTime.now());

        when(taskService.createTask(any(Task.class))).thenReturn(savedTask);

        // When & Then
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskToCreate)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateTask_EmptyTitle() throws Exception {
        // Given
        Task taskToCreate = new Task();
        taskToCreate.setTitle("   "); // Empty title

        // When & Then
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskToCreate)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateTask() throws Exception {
        // Given
        Task taskDetails = new Task();
        taskDetails.setTitle("Updated Task");
        taskDetails.setCompleted(true);

        Task updatedTask = new Task(1L, "Updated Task", true, LocalDateTime.now());

        when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(Optional.of(updatedTask));

        // When & Then
        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDetails)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateTask_NotFound() throws Exception {
        // Given
        Task taskDetails = new Task();
        taskDetails.setTitle("Updated Task");

        when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDetails)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteTask() throws Exception {
        // Given
        when(taskService.deleteTask(1L)).thenReturn(true);

        // When & Then
        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteTask_NotFound() throws Exception {
        // Given
        when(taskService.deleteTask(1L)).thenReturn(false);

        // When & Then
        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isNotFound());
    }
}