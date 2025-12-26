package com.checkInProject.dto;

import com.checkInProject.model.Usuario;
import com.checkInProject.model.ETipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioDTO {

    // Output only
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")

    // Input only
    private String senha;

    @NotBlank(message = "O telefone é obrigatório")
    private String telefone;

    private String role; // Output only

    // DTO -> Entity
    public Usuario toEntity() {
        Usuario usuario = new Usuario();
        usuario.setNome(this.nome);
        usuario.setEmail(this.email);
        usuario.setTelefone(this.telefone);
        usuario.setSenha(this.senha);
        usuario.setRole(ETipoUsuario.PARTICIPANTE);
        usuario.setAtivo(true);

        return usuario;
    }

    // Entity -> DTO
    public static UsuarioDTO fromEntity(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setTelefone(usuario.getTelefone());
        dto.setSenha(null);
        dto.setRole(usuario.getRole().name());
        return dto;
    }
}