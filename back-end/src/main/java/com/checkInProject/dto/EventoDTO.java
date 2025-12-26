package com.checkInProject.dto;

import com.checkInProject.model.Evento;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class EventoDTO {

    private Long id;

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    @Size(max = 5000)
    private String descricao;

    @NotNull(message = "A data do evento é obrigatória")
    @Future(message = "A data do evento deve ser no futuro")
    private LocalDateTime data;

    @NotBlank(message = "O local é obrigatório")
    private String local;

    @NotNull(message = "A quantidade de vagas é obrigatória")
    @Min(value = 1, message = "O evento deve ter pelo menos 1 vaga")
    private Integer vagas;

    private String imagemUrl;

    private Long organizadorId;
    private String organizadorNome;

    //DTO -> Entity
    public Evento toEntity() {
        Evento evento = new Evento();
        evento.setTitulo(this.titulo);
        evento.setDescricao(this.descricao);
        evento.setData(this.data);
        evento.setLocal(this.local);
        evento.setVagas(this.vagas);
        evento.setImagemUrl(this.imagemUrl);
        return evento;
    }

    // Entity -> DTO
    public static EventoDTO fromEntity(Evento evento) {
        EventoDTO dto = new EventoDTO();
        dto.setId(evento.getId());
        dto.setTitulo(evento.getTitulo());
        dto.setDescricao(evento.getDescricao());
        dto.setData(evento.getData());
        dto.setLocal(evento.getLocal());
        dto.setVagas(evento.getVagas());
        dto.setImagemUrl(evento.getImagemUrl());

        if (evento.getUsuarioOrganizador() != null) {
            dto.setOrganizadorId(evento.getUsuarioOrganizador().getId());
            dto.setOrganizadorNome(evento.getUsuarioOrganizador().getNome());
        }

        return dto;
    }
}