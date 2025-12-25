package com.checkInProject.model;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter

public abstract  class EntidadeGenerica {



    @Column(name = "criado_em", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "atualizado_em")
    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersistAudit() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void preUpdateAudit() {
        this.updatedAt = LocalDateTime.now();
    }

}
