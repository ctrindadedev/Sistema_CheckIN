package com.checkInProject.service.inscricao;

import com.checkInProject.dto.request.InscricaoRequestDTO;
import com.checkInProject.dto.response.InscricaoResponseDTO;
import com.checkInProject.exception.RecursoNaoEncontradoException;
import com.checkInProject.exception.RegraDeNegocioException;
import com.checkInProject.model.Evento;
import com.checkInProject.model.Inscricao;
import com.checkInProject.model.Usuario;
import com.checkInProject.repository.inscricao.InscricaoRepository;
import com.checkInProject.service.evento.EventoService;
import com.checkInProject.service.usuario.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;
    private final EventoService eventoService;
    private final UsuarioService usuarioService;

    public InscricaoService(InscricaoRepository inscricaoRepository, EventoService eventoService, UsuarioService usuarioService) {
        this.inscricaoRepository = inscricaoRepository;
        this.eventoService = eventoService;
        this.usuarioService = usuarioService;
    }

    public List<InscricaoResponseDTO> listarTodas() {
        return inscricaoRepository.findAll().stream()
                .map(InscricaoResponseDTO::fromEntityToResponse)
                .toList();
    }

    public List<InscricaoResponseDTO> listarPorUsuarioId(Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);

        return inscricaoRepository.findByUsuario(usuario).stream()
                .map(InscricaoResponseDTO::fromEntityToResponse)
                .toList();
    }

    @Transactional
    public InscricaoResponseDTO realizarInscricao(InscricaoRequestDTO request, Usuario usuarioLogado) {
        Evento evento = eventoService.buscarPorId(request.eventoId());

        if (inscricaoRepository.existsByEventoAndUsuario(evento, usuarioLogado)) {
            throw new RegraDeNegocioException("Você já está inscrito neste evento.");
        }

        if (evento.getVagas() != null) {
            long ocupadas = inscricaoRepository.countByEvento(evento);
            if (ocupadas >= evento.getVagas()) {
                throw new RegraDeNegocioException("Não há vagas disponíveis para este evento.");
            }
        }

        Inscricao novaInscricao = new Inscricao();
        novaInscricao.setEvento(evento);
        novaInscricao.setUsuario(usuarioLogado);

        novaInscricao = inscricaoRepository.save(novaInscricao);

        return InscricaoResponseDTO.fromEntityToResponse(novaInscricao);
    }

    @Transactional
    public void cancelarInscricao(Long inscricaoId) {
        Inscricao existente = inscricaoRepository.findById(inscricaoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Inscrição não encontrada."));
        inscricaoRepository.delete(existente);
    }

    public Optional<Inscricao> possuiInscricao(Long eventoId, Long usuarioId) {
        return inscricaoRepository.findByEventoIdAndUsuarioId(eventoId, usuarioId);
    }
}