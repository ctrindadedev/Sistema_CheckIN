package com.CheckInProject.controller.evento;


import com.checkInProject.CheckInProjectApplication;
import com.checkInProject.config.TokenConfig;
import com.checkInProject.controller.EventoController;
import com.checkInProject.dto.request.EventoRequestDTO;
import com.checkInProject.dto.response.EventoResponseDTO;
import com.checkInProject.service.evento.EventoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EventoController.class)
@ContextConfiguration(classes = CheckInProjectApplication.class)
@AutoConfigureMockMvc(addFilters = false)

class EventoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EventoService eventoService;

    @MockitoBean
    private TokenConfig tokenConfig;

    @Test
    void criarEvento_DeveRetornar201Created() throws Exception {
        EventoRequestDTO request = new EventoRequestDTO("Festa", "Desc", LocalDateTime.now().plusDays(2), "Local", 100, "url");
        EventoResponseDTO response = new EventoResponseDTO(1L, "Festa", "Desc", LocalDateTime.now(), "Local", 100, "url", 1L, "Org");

        when(eventoService.criar(any(), any())).thenReturn(response);

        mockMvc.perform(post("/eventos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}