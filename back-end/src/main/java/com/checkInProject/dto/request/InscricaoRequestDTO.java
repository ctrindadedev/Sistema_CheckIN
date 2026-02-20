package com.checkInProject.dto.request;

import jakarta.validation.constraints.NotNull;

public record InscricaoRequestDTO(
        @NotNull(message = "O ID do evento é obrigatório")
        Long eventoId
) { }