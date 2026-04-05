package com.checkInProject.service.checkin;

import com.checkInProject.dto.response.InscricaoResponseDTO;
import com.checkInProject.exception.RecursoNaoEncontradoException;
import com.checkInProject.exception.RegraDeNegocioException;
import com.checkInProject.model.EStatusCheckInEvento;
import com.checkInProject.model.Inscricao;
import com.checkInProject.repository.inscricao.InscricaoRepository;
import com.checkInProject.service.inscricao.InscricaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckinServiceTest {

    @Mock
    private InscricaoService inscricaoService;

    @Mock
    private InscricaoRepository inscricaoRepository;

    @InjectMocks
    private CheckinService checkinService;

    private Inscricao inscricao;

    @BeforeEach
    void setUp() {
        inscricao = new Inscricao();
        inscricao.setId(1L);
        inscricao.setStatusCheckin(EStatusCheckInEvento.AGUARDANDO_VALIDACAO);
    }

    @Test
    void realizarCheckIn_ComInscricaoValida_DeveRealizarComSucesso() {
        when(inscricaoService.possuiInscricao(1L, 1L)).thenReturn(Optional.of(inscricao));

        InscricaoResponseDTO resultado = checkinService.realizarCheckIn(1L, 1L);

        assertNotNull(resultado.dataCheckin());
        assertEquals(EStatusCheckInEvento.VALIDADO, resultado.statusCheckin());
        verify(inscricaoRepository, times(1)).save(inscricao);
    }

    @Test
    void realizarCheckIn_SemInscricao_DeveLancarRecursoNaoEncontrado() {
        when(inscricaoService.possuiInscricao(1L, 1L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> checkinService.realizarCheckIn(1L, 1L));
        verify(inscricaoRepository, never()).save(any());
    }

    @Test
    void realizarCheckIn_JaRealizado_DeveLancarRegraDeNegocioException() {
        inscricao.setDataCheckin(LocalDateTime.now());
        when(inscricaoService.possuiInscricao(1L, 1L)).thenReturn(Optional.of(inscricao));

        assertThrows(RegraDeNegocioException.class, () -> checkinService.realizarCheckIn(1L, 1L));
        verify(inscricaoRepository, never()).save(any());
    }
}