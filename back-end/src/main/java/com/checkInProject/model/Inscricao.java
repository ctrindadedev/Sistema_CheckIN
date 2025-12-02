package com.checkInProject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "inscricoes")
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "evento_id", nullable = false)
    private Long eventoId;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    private LocalDateTime dataCheckin;

    public Inscricao() {
    }

    public Inscricao(Long eventoId, Long usuarioId, LocalDateTime dataCheckin) {
        this.eventoId = eventoId;
        this.usuarioId = usuarioId;
        this.dataCheckin = dataCheckin;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getEventoId() { return eventoId; }
    public void setEventoId(Long eventoId) { this.eventoId = eventoId; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public LocalDateTime getDataCheckin() { return dataCheckin; }
    public void setDataCheckin(LocalDateTime dataCheckin) { this.dataCheckin = dataCheckin; }
}