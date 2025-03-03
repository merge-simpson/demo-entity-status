package com.example.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.Objects;

@Entity
@Getter
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    // TODO use converter to board.status
    @Enumerated(EnumType.STRING)
    private BoardStatus status;

    @Column(updatable = false)
    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @Builder(
            builderClassName = "UpdateBuilder",
            builderMethodName = "prepareUpdate",
            buildMethodName = "update"
    )
    public void updateEntity(String title, String content, BoardStatus status) {
        Objects.requireNonNull(title, "Title cannot be null");
        Objects.requireNonNull(content, "Content cannot be null");
        Objects.requireNonNull(status, "Status cannot be null");

        this.title = title;
        this.content = content;
        this.status = status;
    }

    public void softDelete() {
        this.status = BoardStatus.REMOVED;
    }
}
