package com.checkInProject.dto.request;
import com.checkInProject.model.ETipoUsuario;
import jakarta.validation.constraints.NotEmpty;

public record RegisterUsuarioRequest(@NotEmpty(message = "Nome é obrigatório") String name,
                                  @NotEmpty(message = "E-mail é obrigatório") String email,
                                  @NotEmpty(message = "Senha é obrigatória") String password,
                                  ETipoUsuario role) {
}
