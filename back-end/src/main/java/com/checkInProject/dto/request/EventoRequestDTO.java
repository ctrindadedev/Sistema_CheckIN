package com.checkInProject.dto.request;

import com.checkInProject.dto.response.EventoResponseDTO;
import com.checkInProject.model.Evento;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record EventoRequestDTO(
        @NotBlank(message = "O título é obrigatório") String titulo,
        @Size(max = 5000) String descricao,
        @NotNull @Future(message = "A data do evento deve ser no futuro") LocalDateTime data,
        @NotBlank(message = "O local é obrigatório") String local,
        @NotNull @Min(value = 1) Integer vagas,
        String imagemUrl
) { }