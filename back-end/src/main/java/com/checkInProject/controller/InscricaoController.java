package com.checkInProject.controller;


import com.checkInProject.model.Inscricao;
import com.checkInProject.service.inscricao.InscricaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inscricao")
public class InscricaoController

{
    private final InscricaoService inscricaoService;

    public InscricaoController(InscricaoService inscricaoService) {
        this.inscricaoService = inscricaoService;
    }

    @GetMapping
    public ResponseEntity<List<Inscricao>> listarTodos() {
        return ResponseEntity.ok(inscricaoService.listarTodas());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Inscricao>> listarPorUsuarioId(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(inscricaoService.listarPorUsuarioId(usuarioId));
    }

    @DeleteMapping("/{inscricaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable Long inscricaoId) {
        inscricaoService.cancelarInscricao(inscricaoId);
    }

}
