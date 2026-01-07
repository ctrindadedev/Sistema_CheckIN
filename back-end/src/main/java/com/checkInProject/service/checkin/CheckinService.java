package com.checkInProject.service.checkin;

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

    public Inscricao realizarCheckIn(Long eventoId, Long usuarioId) {

        Inscricao inscricao = inscricaoService.possuiInscricao(eventoId, usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscrição não encontrada para este usuário neste evento."));

        if (inscricao.getDataCheckin() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Check-in já foi realizado anteriormente.");
        }
        inscricao.setDataCheckin(LocalDateTime.now());
        inscricao.setStatusCheckin(EStatusCheckInEvento.VALIDADO);

        inscricaoRepository.save(inscricao);

        return inscricao;
    }

    public void cancelarCheckIn(Long eventoId, Long usuarioId) {
        Inscricao inscricao = inscricaoService.possuiInscricao(eventoId, usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscrição não encontrada."));

        inscricao.setDataCheckin(null);
        inscricao.setStatusCheckin(EStatusCheckInEvento.AGUARDANDO_VALIDACAO);
        inscricaoRepository.save(inscricao);
    }
}
