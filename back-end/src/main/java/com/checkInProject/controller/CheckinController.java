package com.checkInProject.controller;

import com.checkInProject.dto.CheckinRequest;
import com.checkInProject.model.Inscricao;
import com.checkInProject.service.inscricao.InscricaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkin")
@CrossOrigin(origins = "*")
public class CheckinController {

    private final InscricaoService inscricaoService;

    public CheckinController(InscricaoService inscricaoService) {
        this.inscricaoService = inscricaoService;
    }

    @GetMapping
    public ResponseEntity<List<Inscricao>> listarTodos() {
        return ResponseEntity.ok(inscricaoService.listarTodas());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Inscricao>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(inscricaoService.listarPorUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<Inscricao> realizarCheckin(@Valid @RequestBody CheckinRequest request) {
        Inscricao inscricao = inscricaoService.realizarCheckin(request.eventoId(), request.usuarioId());
        return ResponseEntity.status(HttpStatus.CREATED).body(inscricao);
    }

    @DeleteMapping("/{inscricaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable Long inscricaoId) {
        inscricaoService.cancelarInscricao(inscricaoId);
    }
}