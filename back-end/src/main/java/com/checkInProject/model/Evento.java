package com.checkInProject.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "eventos_tb")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class Evento extends  EntidadeGenerica {

    @Id
    @Column(name = "evento_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(length = 5000)
    private String descricao;

    @Column(name = "data_evento")
    private LocalDateTime data;

    @Column(name = "local_evento")
    private String local;

    private Integer vagas;

    @Column(length = 1000)
    private String imagemUrl;

    @ManyToOne
    @JoinColumn(name = "usuario_organizador_id")
    private Usuario usuarioOrganizador;

    @Column(name = "criado_em")
    private LocalDateTime createdAt;

    @Column(name = "atualizado_em")
    private LocalDateTime updatedAt;

}