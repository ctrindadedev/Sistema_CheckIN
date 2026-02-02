package com.checkInProject.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="usuario_tb")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
@ToString
public class Usuario extends  EntidadeGenerica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="usuario_id")
    private Long id;

    private String nome;

    @Column(unique = true)
    private String email;

    private String senha;

    @Column(unique = true)
    private String telefone;

    private Boolean ativo;

    @Column(name = "criado_em")
    private LocalDateTime createdAt;

    @Column(name = "atualizado_em")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private ETipoUsuario role;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "perfis_usuario", joinColumns = @JoinColumn(name = "usuario_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<ETipoUsuario> roles = new HashSet<>();

    @PrePersist
    private void prePersistUsuario() {
        this.ativo = Boolean.TRUE;
        if (this.roles == null || this.roles.isEmpty()) {
            this.roles = new HashSet<>();
            this.roles.add(ETipoUsuario.PARTICIPANTE);
        }
    }
}