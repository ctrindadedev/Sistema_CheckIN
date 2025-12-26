package com.checkInProject.dto;

import com.checkInProject.model.EStatusCheckInEvento;
import com.checkInProject.model.Evento;
import com.checkInProject.model.Inscricao;
import com.checkInProject.model.Usuario;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class InscricaoDTO {

    private Long id;

    @NotNull(message = "ID do evento é obrigatório")
    private Long eventoId;

    private Long usuarioId;

    // Apenas Output
    private String nomeUsuario;
    private String nomeEvento;
    private String status;
    private LocalDateTime dataCheckin;

    // DTO -> Entity
    public Inscricao toEntity(Usuario usuario, Evento evento) {
        Inscricao inscricao = new Inscricao();
        inscricao.setUsuario(usuario);
        inscricao.setEvento(evento);
        return inscricao;
    }

    // Entity -> DTO
    public static InscricaoDTO fromEntity(Inscricao inscricao) {
        InscricaoDTO dto = new InscricaoDTO();
        dto.setId(inscricao.getId());
        dto.setEventoId(inscricao.getEvento().getId());
        dto.setUsuarioId(inscricao.getUsuario().getId());

        // Dados enriquecidos para o Frontend
        dto.setNomeUsuario(inscricao.getUsuario().getNome());
        dto.setNomeEvento(inscricao.getEvento().getTitulo());

        if (inscricao.getStatusCheckin() != null) {
            dto.setStatus(inscricao.getStatusCheckin().name());
        }

        dto.setDataCheckin(inscricao.getDataCheckin());

        return dto;
    }
}