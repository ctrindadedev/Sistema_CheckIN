package com.checkInProject.service.inscricao;

import com.checkInProject.dto.request.InscricaoRequestDTO;
import com.checkInProject.dto.response.InscricaoResponseDTO;
import com.checkInProject.exception.RecursoNaoEncontradoException;
import com.checkInProject.exception.RegraDeNegocioException;
import com.checkInProject.model.Evento;
import com.checkInProject.model.Inscricao;
import com.checkInProject.model.Usuario;
import com.checkInProject.repository.inscricao.InscricaoRepository;
import com.checkInProject.service.evento.EventoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InscricaoServiceTest {

    @Mock
    private InscricaoRepository inscricaoRepository;

    @Mock
    private EventoService eventoService;

    @InjectMocks
    private InscricaoService inscricaoService;

    private Usuario usuario;
    private Evento evento;
    private Inscricao inscricao;
    private InscricaoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João");

        evento = new Evento();
        evento.setId(1L);
        evento.setTitulo("Tech Summit");
        evento.setVagas(10);

        inscricao = new Inscricao();
        inscricao.setId(1L);
        inscricao.setUsuario(usuario);
        inscricao.setEvento(evento);

        requestDTO = new InscricaoRequestDTO(1L);
    }

    @Test
    void realizarInscricao_ComSucesso_DeveRetornarResponseDTO() {
        when(eventoService.buscarPorId(1L)).thenReturn(evento);
        when(inscricaoRepository.existsByEventoAndUsuario(evento, usuario)).thenReturn(false);
        when(inscricaoRepository.countByEvento(evento)).thenReturn(5);

        when(inscricaoRepository.save(any(Inscricao.class))).thenReturn(inscricao);

        InscricaoResponseDTO response = inscricaoService.realizarInscricao(requestDTO, usuario);

        assertNotNull(response);
        assertEquals("Tech Summit", response.nomeEvento());
        assertEquals("João", response.nomeUsuario());
        verify(inscricaoRepository, times(1)).save(any(Inscricao.class));
    }

    @Test
    void realizarInscricao_UsuarioJaInscrito_DeveLancarRegraDeNegocioException() {
        when(eventoService.buscarPorId(1L)).thenReturn(evento);
        when(inscricaoRepository.existsByEventoAndUsuario(evento, usuario)).thenReturn(true);

        assertThrows(RegraDeNegocioException.class, () -> inscricaoService.realizarInscricao(requestDTO, usuario));
        verify(inscricaoRepository, never()).save(any(Inscricao.class));
    }

    @Test
    void realizarInscricao_EventoSemVagas_DeveLancarRegraDeNegocioException() {
        when(eventoService.buscarPorId(1L)).thenReturn(evento);
        when(inscricaoRepository.existsByEventoAndUsuario(evento, usuario)).thenReturn(false);
        when(inscricaoRepository.countByEvento(evento)).thenReturn(10);

        assertThrows(RegraDeNegocioException.class, () -> inscricaoService.realizarInscricao(requestDTO, usuario));
        verify(inscricaoRepository, never()).save(any(Inscricao.class));
    }

    @Test
    void cancelarInscricao_Existente_DeveDeletarComSucesso() {
        when(inscricaoRepository.findById(1L)).thenReturn(Optional.of(inscricao));

        inscricaoService.cancelarInscricao(1L);

        verify(inscricaoRepository, times(1)).delete(inscricao);
    }

    @Test
    void cancelarInscricao_NaoExistente_DeveLancarRecursoNaoEncontradoException() {
        when(inscricaoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> inscricaoService.cancelarInscricao(99L));
        verify(inscricaoRepository, never()).delete(any(Inscricao.class));
    }
}