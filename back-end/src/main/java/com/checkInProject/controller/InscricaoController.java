package com.checkInProject.controller;

import com.checkInProject.dto.request.InscricaoRequestDTO;
import com.checkInProject.dto.response.InscricaoResponseDTO;
import com.checkInProject.model.Usuario;
import com.checkInProject.service.inscricao.InscricaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inscricao")
public class InscricaoController {

    private final InscricaoService inscricaoService;

    public InscricaoController(InscricaoService inscricaoService) {
        this.inscricaoService = inscricaoService;
    }

    @PostMapping
    public ResponseEntity<InscricaoResponseDTO> inscrever(
            @Valid @RequestBody InscricaoRequestDTO request,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        InscricaoResponseDTO response = inscricaoService.realizarInscricao(request, usuarioLogado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<InscricaoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(inscricaoService.listarTodas());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<InscricaoResponseDTO>> listarPorUsuarioId(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(inscricaoService.listarPorUsuarioId(usuarioId));
    }

    @DeleteMapping("/{inscricaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable Long inscricaoId) {
        inscricaoService.cancelarInscricao(inscricaoId);
    }
}