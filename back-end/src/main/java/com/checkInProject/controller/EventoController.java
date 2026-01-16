package com.checkInProject.controller;

import com.checkInProject.dto.EventoDTO;
import com.checkInProject.model.Evento;
import com.checkInProject.model.Usuario;
import com.checkInProject.service.evento.EventoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Valid
    @PostMapping
    public ResponseEntity<Evento> criarEvento(@RequestBody EventoDTO eventoDto, Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.criar(eventoDto, usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> atualizarEvento(@PathVariable Long id, @RequestBody Evento evento,  Usuario usuario) {
        return ResponseEntity.ok(eventoService.atualizar(id, evento, usuario));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerEvento(@PathVariable Long id) {
        eventoService.remover(id);
    }
}