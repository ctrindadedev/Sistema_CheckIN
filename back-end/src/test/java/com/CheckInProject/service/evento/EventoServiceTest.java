package com.CheckInProject.service.evento;

import com.checkInProject.dto.request.EventoRequestDTO;
import com.checkInProject.dto.response.EventoResponseDTO;
import com.checkInProject.exception.RecursoNaoEncontradoException;
import com.checkInProject.exception.RegraDeNegocioException;
import com.checkInProject.model.ETipoUsuario;
import com.checkInProject.model.Evento;
import com.checkInProject.model.Usuario;
import com.checkInProject.repository.evento.EventoRepository;
import com.checkInProject.service.evento.EventoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventoServiceTest {

    @Mock
    private EventoRepository eventoRepository;

    @InjectMocks
    private EventoService eventoService;

    private Usuario usuarioOrganizador;
    private Usuario usuarioParticipante;
    private EventoRequestDTO requestDTO;
    private Evento evento;

    @BeforeEach
    void setUp() {
        usuarioOrganizador = new Usuario();
        usuarioOrganizador.setId(1L);
        usuarioOrganizador.setRoles(Set.of(ETipoUsuario.ORGANIZADOR));

        usuarioParticipante = new Usuario();
        usuarioParticipante.setId(2L);
        usuarioParticipante.setRoles(Set.of(ETipoUsuario.PARTICIPANTE));

        requestDTO = new EventoRequestDTO("Festa", "Desc", LocalDateTime.now().plusDays(2), "Local", 100, "url");

        evento = new Evento();
        evento.setId(1L);
        evento.setTitulo("Festa");
        evento.setUsuarioOrganizador(usuarioOrganizador);
    }

    @Test
    void criar_ComUsuarioOrganizador_DeveRetornarResponseDTO() {
        when(eventoRepository.save(any(Evento.class))).thenReturn(evento);

        EventoResponseDTO response = eventoService.criar(requestDTO, usuarioOrganizador);

        assertNotNull(response);
        assertEquals("Festa", response.titulo());
        verify(eventoRepository, times(1)).save(any(Evento.class));
    }

    @Test
    void criar_ComUsuarioParticipante_DeveLancarRegraDeNegocioException() {
        assertThrows(RegraDeNegocioException.class, () -> eventoService.criar(requestDTO, usuarioParticipante));
        verify(eventoRepository, never()).save(any(Evento.class));
    }

    @Test
    void buscarPorId_Existente_DeveRetornarResponseDTO() {
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(evento));

        Evento resultado = eventoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void buscarPorId_NaoExistente_DeveLancarRecursoNaoEncontradoException() {
        when(eventoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> eventoService.buscarPorId(99L));
    }
}