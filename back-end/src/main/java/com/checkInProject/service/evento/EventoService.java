package com.checkInProject.service.evento;

import com.checkInProject.model.Evento;
import com.checkInProject.repository.evento.EventoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado"));
    }

    @Transactional
    public Evento criar(Evento evento) {
        evento.setId(null);
        return eventoRepository.save(evento);
    }

    @Transactional
    public Evento atualizar(Long id, Evento dadosAtualizados) {
        Evento existente = buscarPorId(id);
        existente.setTitulo(dadosAtualizados.getTitulo());
        existente.setDescricao(dadosAtualizados.getDescricao());
        existente.setData(dadosAtualizados.getData());
        existente.setLocal(dadosAtualizados.getLocal());
        existente.setVagas(dadosAtualizados.getVagas());
        existente.setImagemUrl(dadosAtualizados.getImagemUrl());
        return eventoRepository.save(existente);
    }

    @Transactional
    public void remover(Long id) {
        Evento existente = buscarPorId(id);
        eventoRepository.delete(existente);
    }
}