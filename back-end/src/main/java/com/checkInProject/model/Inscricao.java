package com.checkInProject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "evento_inscricoes")
public class Inscricao extends  EntidadeGenerica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "data_checkin")
    private LocalDateTime dataCheckin;

    @Enumerated(EnumType.STRING)
    private EStatusCheckInEvento statusCheckin;

    @Column(name = "criado_em")
    private LocalDateTime createdAt;

    @Column(name = "atualizado_em")
    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersistInscricao() {
        if (this.statusCheckin == null) {
            this.statusCheckin = EStatusCheckInEvento.AGUARDANDO_VALIDACAO;

        }
    }
}