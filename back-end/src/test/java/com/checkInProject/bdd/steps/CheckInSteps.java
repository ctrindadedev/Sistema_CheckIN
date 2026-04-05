package com.checkInProject.bdd.steps;

import com.checkInProject.bdd.config.CucumberSpringConfiguration;
import com.checkInProject.dto.request.CheckinRequest;
import com.checkInProject.dto.response.InscricaoResponseDTO;
import com.checkInProject.service.checkin.CheckinService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CheckInSteps extends CucumberSpringConfiguration {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CheckinService checkinService;

    private Long usuarioId;
    private Long eventoId;
    private ResultActions resultActions;

    @Dado("que eu tenho um usuário válido com ID {long}")
    public void queEuTenhoUmUsuarioValidoComID(Long id) {
        this.usuarioId = id;
    }

    @Dado("eu informo o evento de ID {long}")
    public void euInformoOEventoDeID(Long id) {
        this.eventoId = id;

        InscricaoResponseDTO mockResponse = new InscricaoResponseDTO(
                1L, this.eventoId, this.usuarioId,"Nome Mock", "Evento Mock", "CONFIRMADO", null
        );
        when(checkinService.realizarCheckIn(this.eventoId, this.usuarioId)).thenReturn(mockResponse);
    }

    @Dado("eu não informo o ID do evento")
    public void euNaoInformoOIDDoEvento() {
        this.eventoId = null;
    }

    @Quando("eu envio a requisição de check-in")
    public void euEnvioARequisicaoDeCheckIn() throws Exception {
        CheckinRequest request = new CheckinRequest(this.eventoId, this.usuarioId);

        this.resultActions = mockMvc.perform(post("/checkin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }

    @Então("o sistema deve retornar o status {int} \\(OK)")
    public void oSistemaDeveRetornarOStatusOK(int statusCode) throws Exception {
        this.resultActions.andExpect(status().is(statusCode));
    }

    @Então("a resposta deve conter a inscrição realizada")
    public void aRespostaDeveConterAInscricaoRealizada() throws Exception {
        this.resultActions.andExpect(jsonPath("$.eventoId").value(this.eventoId));
    }

    @Então("o sistema deve retornar o status {int} \\(Bad Request)")
    public void oSistemaDeveRetornarOStatusBadRequest(int statusCode) throws Exception {
        this.resultActions.andExpect(status().is(statusCode));
    }
}