package com.checkInProject.dto.response;

import com.checkInProject.model.Inscricao;
import java.time.LocalDateTime;

public record InscricaoResponseDTO(
        Long id,
        Long eventoId,
        Long usuarioId,
        String nomeUsuario,
        String nomeEvento,
        String status,
        LocalDateTime dataCheckin
) {
    public static InscricaoResponseDTO fromEntityToResponse(Inscricao inscricao) {
        String statusCheckin = inscricao.getStatusCheckin() != null
                ? inscricao.getStatusCheckin().name()
                : null;

        return new InscricaoResponseDTO(
                inscricao.getId(),
                inscricao.getEvento().getId(),
                inscricao.getUsuario().getId(),
                inscricao.getUsuario().getNome(),
                inscricao.getEvento().getTitulo(),
                statusCheckin,
                inscricao.getDataCheckin()
        );
    }
}