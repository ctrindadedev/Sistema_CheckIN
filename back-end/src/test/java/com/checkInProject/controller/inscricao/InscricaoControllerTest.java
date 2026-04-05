package com.checkInProject.controller.inscricao;

import com.checkInProject.CheckInProjectApplication;
import com.checkInProject.config.TokenConfig;
import com.checkInProject.controller.InscricaoController;
import com.checkInProject.dto.request.InscricaoRequestDTO;
import com.checkInProject.dto.response.InscricaoResponseDTO;
import com.checkInProject.service.inscricao.InscricaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = InscricaoController.class)
// Desativa o Spring Security para focar só na rota
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = CheckInProjectApplication.class)

class InscricaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private InscricaoService inscricaoService;

    @MockitoBean
    private TokenConfig tokenConfig;

    @Test
    void inscrever_ComDadosValidos_DeveRetornar201Created() throws Exception {
        InscricaoRequestDTO request = new InscricaoRequestDTO(1L);

        InscricaoResponseDTO response = new InscricaoResponseDTO(
                1L, 1L, 1L, "João", "Tech Summit", "AGUARDANDO_VALIDACAO", null
        );

        when(inscricaoService.realizarInscricao(any(), any())).thenReturn(response);

        mockMvc.perform(post("/inscricao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nomeUsuario").value("João"))
                .andExpect(jsonPath("$.nomeEvento").value("Tech Summit"));
    }

    @Test
    void cancelar_DeveRetornar204NoContent() throws Exception {
        doNothing().when(inscricaoService).cancelarInscricao(1L);

        mockMvc.perform(delete("/inscricao/{inscricaoId}", 1L))
                .andExpect(status().isNoContent());
    }
}