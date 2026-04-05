package com.checkInProject.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name="usuario_tb")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
@ToString(exclude = {"roles", "senha"})
public class Usuario extends  EntidadeGenerica implements UserDetails {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() {
        return this.ativo;
    }


}