package com.checkInProject.service.evento;

import com.checkInProject.dto.EventoDTO;
import com.checkInProject.model.ETipoUsuario;
import com.checkInProject.model.Evento;
import com.checkInProject.model.Usuario;
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
    public Evento criar(EventoDTO evento, Usuario usuario) {
            if (!(usuario.getRole() == ETipoUsuario.ORGANIZADOR)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Usuário não é um organizador");
            }
            Evento eventoToSave;
            eventoToSave = evento.toEntity();
            return eventoRepository.save(eventoToSave);
        };

    @Transactional
    public Evento atualizar(Long id, Evento dadosAtualizados,  Usuario usuario) {
        if (!(usuario.getRole() == ETipoUsuario.ORGANIZADOR)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Usuário não é um organizador");

        }
        Evento eventoexistente = buscarPorId(id);
        EventoDTO eventoToUpdate = EventoDTO.fromEntity(eventoexistente);
        eventoToUpdate.setTitulo(dadosAtualizados.getTitulo());
        eventoToUpdate.setDescricao(dadosAtualizados.getDescricao());
        eventoToUpdate.setData(dadosAtualizados.getData());
        eventoToUpdate.setLocal(dadosAtualizados.getLocal());
        eventoToUpdate.setVagas(dadosAtualizados.getVagas());
        eventoToUpdate.setImagemUrl(dadosAtualizados.getImagemUrl());
        return eventoRepository.save(eventoToUpdate.toEntity());
    }

    @Transactional
    public void remover(Long id) {
        Evento existente = buscarPorId(id);
        if (existente != null) {
            eventoRepository.delete(existente);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Evento com o ID informado não existe");
    }
}