package com.checkInProject.controller;

import com.checkInProject.dto.EventoDTO;
import com.checkInProject.dto.request.EventoRequestDTO;
import com.checkInProject.dto.response.EventoResponseDTO;
import com.checkInProject.model.Evento;
import com.checkInProject.model.Usuario;
import com.checkInProject.service.evento.EventoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos")
@CrossOrigin(origins = "*")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public ResponseEntity<List<Evento>> listarEventos() {
        return ResponseEntity.ok(eventoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<EventoResponseDTO> criarEvento(
            @Valid @RequestBody EventoRequestDTO requestDto,
            @AuthenticationPrincipal Usuario usuario) {
        EventoResponseDTO response = eventoService.criar(requestDto, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> atualizarEvento(
            @PathVariable Long id,
            @Valid @RequestBody EventoRequestDTO requestDto,
            @AuthenticationPrincipal Usuario usuario
    ) {
        EventoResponseDTO response = eventoService.atualizar(id, requestDto, usuario);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerEvento(@PathVariable Long id) {
        eventoService.remover(id);
    }
}