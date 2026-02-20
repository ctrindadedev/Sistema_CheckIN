package com.checkInProject.service.evento;

import com.checkInProject.dto.request.EventoRequestDTO;
import com.checkInProject.dto.response.EventoResponseDTO;
import com.checkInProject.exception.RecursoNaoEncontradoException;
import com.checkInProject.exception.RegraDeNegocioException;
import com.checkInProject.model.ETipoUsuario;
import com.checkInProject.model.Evento;
import com.checkInProject.model.Usuario;
import com.checkInProject.repository.evento.EventoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }

    public Evento buscarPorId(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado com o ID: " + id));
    }

    @Transactional
    public EventoResponseDTO criar(EventoRequestDTO request, Usuario usuario) {
        if (!(usuario.getRoles().contains(ETipoUsuario.ORGANIZADOR))) {
            throw new RegraDeNegocioException("O utilizador precisa de ser um organizador.");
        }
        Evento evento = new Evento();
        evento.setTitulo(request.titulo());
        evento.setDescricao(request.descricao());
        evento.setData(request.data());
        evento.setLocal(request.local());
        evento.setVagas(request.vagas());
        evento.setImagemUrl(request.imagemUrl());
        evento.setUsuarioOrganizador(usuario);

        evento = eventoRepository.save(evento);

        return EventoResponseDTO.fromEntityToResponse(evento);
    }

    @Transactional
    public EventoResponseDTO atualizar(Long id, EventoRequestDTO request, Usuario usuario) {
        if (!(usuario.getRoles().contains(ETipoUsuario.ORGANIZADOR))) {
            throw new RegraDeNegocioException("O utilizador precisa de ser um organizador.");
        }

        Evento eventoExistente = eventoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado."));

        eventoExistente.setTitulo(request.titulo());
        eventoExistente.setDescricao(request.descricao());
        eventoExistente.setData(request.data());
        eventoExistente.setLocal(request.local());
        eventoExistente.setVagas(request.vagas());
        eventoExistente.setImagemUrl(request.imagemUrl());

        eventoExistente = eventoRepository.save(eventoExistente);

        return EventoResponseDTO.fromEntityToResponse(eventoExistente);
    }

    @Transactional
    public void remover(Long id) {
        Evento existente = buscarPorId(id);
        if (existente == null) throw new RecursoNaoEncontradoException("Evento não encontrado com o ID: " + id);

        eventoRepository.delete(existente);

    }
}