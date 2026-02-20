package com.checkInProject.dto.response;

import com.checkInProject.model.Evento;

import java.time.LocalDateTime;

public record EventoResponseDTO(
        Long id,
        String titulo,
        String descricao,
        LocalDateTime data,
        String local,
        Integer vagas,
        String imagemUrl,
        Long organizadorId,
        String organizadorNome
) {

    public static EventoResponseDTO fromEntityToResponse(Evento evento) {
        Long organizadorId = null;
        String organizadorNome = null;

        if (evento.getUsuarioOrganizador() != null) {
            organizadorId = evento.getUsuarioOrganizador().getId();
            organizadorNome = evento.getUsuarioOrganizador().getNome();
        }
        return new EventoResponseDTO(
                evento.getId(),
                evento.getTitulo(),
                evento.getDescricao(),
                evento.getData(),
                evento.getLocal(),
                evento.getVagas(),
                evento.getImagemUrl(),
                organizadorId,
                organizadorNome
        );
    }
}
