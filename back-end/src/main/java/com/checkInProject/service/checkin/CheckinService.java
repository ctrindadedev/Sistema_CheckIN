package com.checkInProject.service.checkin;

import com.checkInProject.dto.response.InscricaoResponseDTO;
import com.checkInProject.exception.RecursoNaoEncontradoException;
import com.checkInProject.exception.RegraDeNegocioException;
import com.checkInProject.model.EStatusCheckInEvento;
import com.checkInProject.model.Evento;
import com.checkInProject.model.Inscricao;
import com.checkInProject.repository.inscricao.InscricaoRepository;
import com.checkInProject.service.evento.EventoService;
import com.checkInProject.service.inscricao.InscricaoService;
import com.checkInProject.service.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CheckinService {

    private final InscricaoService inscricaoService;
    private final InscricaoRepository inscricaoRepository;

    public InscricaoResponseDTO realizarCheckIn(Long eventoId, Long usuarioId) {

        if (eventoId == null || usuarioId == null) {
            throw new RegraDeNegocioException("O check-in só pode ser realizado com números de ID de evento e usuário válidos.");
        }

        Inscricao inscricao = inscricaoService.possuiInscricao(eventoId, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Inscrição não encontrada, é necessário estar inscrito no evento antes de fazer check-in."));

        if (inscricao.getDataCheckin() != null) {
            throw new RegraDeNegocioException("O check-in para esta inscrição já foi realizado anteriormente.");
        }

        inscricao.setDataCheckin(LocalDateTime.now());
        inscricao.setStatusCheckin(EStatusCheckInEvento.VALIDADO);

        inscricaoRepository.save(inscricao);

        return InscricaoResponseDTO.fromEntityToResponse(inscricao);
    }

    public void cancelarCheckIn(Long eventoId, Long usuarioId) {
        Inscricao inscricao = inscricaoService.possuiInscricao(eventoId, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Inscrição não encontrada, é necessário estar inscrito no evento antes de fazer check-in."));

        inscricao.setDataCheckin(null);
        inscricao.setStatusCheckin(EStatusCheckInEvento.AGUARDANDO_VALIDACAO);
        inscricaoRepository.save(inscricao);
    }
}
