package com.checkInProject.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Escuta a RecursoNaoEncontradoException
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroPadrao> entityNotFound(RecursoNaoEncontradoException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        ErroPadrao erro = ErroPadrao.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Recurso não encontrado")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(erro);
    }

    // Escuta a RegraDeNegocioException
    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ErroPadrao> businessRuleViolation(RegraDeNegocioException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErroPadrao erro = ErroPadrao.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Violação de regra de negócio")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(erro);
    }

    // Captura exceções genéricas para não vazar stacktrace
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroPadrao> handleGenericException(Exception e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ErroPadrao erro = ErroPadrao.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Erro interno do servidor")
                .message("Ocorreu um erro inesperado.")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(erro);
    }
}