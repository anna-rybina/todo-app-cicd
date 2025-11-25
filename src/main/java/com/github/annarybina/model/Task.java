package com.github.annarybina.model;

import java.time.LocalDateTime;

public class Task {
    private Long id;
    private String title;
    private Boolean completed;
    private LocalDateTime createdAt;

    // Конструкторы, геттеры и сеттеры
    public Task() {}

    public Task(Long id, String title, Boolean completed, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.createdAt = createdAt;
    }

    // Геттеры и сеттеры для всех полей
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Boolean getCompleted() { return completed; }
    public void setCompleted(Boolean completed) { this.completed = completed; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
