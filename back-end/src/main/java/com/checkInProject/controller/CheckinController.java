package com.checkInProject.controller;

import com.checkInProject.dto.CheckinRequest;
import com.checkInProject.model.Inscricao;
import com.checkInProject.service.checkin.CheckinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CheckinController {

    private final CheckinService checkinService;

    @PostMapping
    public ResponseEntity<Inscricao> realizarCheckin(@Valid @RequestBody CheckinRequest request) {
        Inscricao inscricaoRealizada = checkinService.realizarCheckIn(
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