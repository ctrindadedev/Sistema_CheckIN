package com.checkInProject.controller;

import com.checkInProject.dto.request.CheckinRequest;
import com.checkInProject.dto.response.InscricaoResponseDTO;
import com.checkInProject.model.Inscricao;
import com.checkInProject.service.checkin.CheckinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CheckinController {

    private final CheckinService checkinService;

    @PostMapping
    public ResponseEntity<InscricaoResponseDTO> realizarCheckin(@Valid @RequestBody CheckinRequest request) {
        InscricaoResponseDTO inscricaoRealizada = checkinService.realizarCheckIn(
                request.eventoId(),
                request.usuarioId()
        );

        return ResponseEntity.ok(inscricaoRealizada);
    }

    @DeleteMapping
    public ResponseEntity<Void> cancelarCheckin(@Valid @RequestBody CheckinRequest request) {
        checkinService.cancelarCheckIn(request.eventoId(), request.usuarioId());
        return ResponseEntity.noContent().build();
    }

}