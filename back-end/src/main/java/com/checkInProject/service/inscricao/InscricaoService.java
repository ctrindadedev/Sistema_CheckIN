package com.checkInProject.service.inscricao;

import com.checkInProject.dto.InscricaoDTO;
import com.checkInProject.exception.RecursoNaoEncontradoException;
import com.checkInProject.exception.RegraDeNegocioException;
import com.checkInProject.model.Evento;
import com.checkInProject.model.Inscricao;
import com.checkInProject.model.Usuario;
import com.checkInProject.repository.inscricao.InscricaoRepository;
import com.checkInProject.service.evento.EventoService;
import com.checkInProject.service.usuario.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;
    private final EventoService eventoService;
    private  final UsuarioService usuarioService;


    public InscricaoService(InscricaoRepository inscricaoRepository, EventoService eventoService, UsuarioService usuarioService) {
        this.inscricaoRepository = inscricaoRepository;
        this.eventoService = eventoService;
        this.usuarioService = usuarioService;
    }

    public List<Inscricao> listarTodas() {
        return inscricaoRepository.findAll();
    }

    public List<Inscricao> listarPorUsuarioId(Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario.getId() == null || !usuario.getId().equals(id)){
            throw new RecursoNaoEncontradoException("Usuário informado não existe");
        }
        return inscricaoRepository.findByUsuario(usuario);
    }

    @Transactional
    public Inscricao realizarInscricao(Long eventoId, Long usuarioId) {

        Evento evento = eventoService.buscarPorId(eventoId);
        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        if (inscricaoRepository.existsByEventoAndUsuario(evento, usuario)) {
            throw new RegraDeNegocioException("Usuário já inscrito neste evento");
        }
        if (evento.getVagas() != null) {
            long ocupadas = inscricaoRepository.countByEvento(evento);
            if (ocupadas >= evento.getVagas()) {
                throw new RecursoNaoEncontradoException("Não há vagas disponíveis");
            }
        }
        InscricaoDTO inscricao = new InscricaoDTO();
        Inscricao inscricaoToSave = inscricao.toEntity(usuario, evento);

        InscricaoDTO.fromEntity(inscricaoToSave);
        inscricaoToSave.setEvento(evento);
        inscricaoToSave.setUsuario(usuario);

        return inscricaoRepository.save(inscricaoToSave);
    }

    @Transactional
    public void cancelarInscricao(Long inscricaoId) {
        Inscricao existente = inscricaoRepository.findById(inscricaoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Inscrição não encontrada"));
        inscricaoRepository.delete(existente);
    }

    public Optional<Inscricao> possuiInscricao(Long eventoId, Long usuarioId) {
        return inscricaoRepository.findByEventoIdAndUsuarioId(eventoId, usuarioId);
    }
}