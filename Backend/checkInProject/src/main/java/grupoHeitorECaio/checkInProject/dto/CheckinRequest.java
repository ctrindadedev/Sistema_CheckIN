package grupoHeitorECaio.checkInProject.dto;

import jakarta.validation.constraints.NotNull;

public record CheckinRequest(
        @NotNull(message = "O id do evento é obrigatório")
        Long eventoId,

        @NotNull(message = "O id do usuário é obrigatório")
        Long usuarioId
) {
}

