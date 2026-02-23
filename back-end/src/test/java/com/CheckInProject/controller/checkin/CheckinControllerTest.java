package com.CheckInProject.controller.checkin;


import com.checkInProject.controller.CheckinController;
import com.checkInProject.dto.request.CheckinRequest;
import com.checkInProject.dto.response.InscricaoResponseDTO;
import com.checkInProject.model.Inscricao;
import com.checkInProject.service.checkin.CheckinService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CheckinController.class)
@AutoConfigureMockMvc(addFilters = false)
class CheckinControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CheckinService checkinService;

    @Test
    void realizarCheckin_DeveRetornar200Ok() throws Exception {
        CheckinRequest request = new CheckinRequest(1L, 1L);
        Inscricao inscricaoMokada = new Inscricao();

        when(checkinService.realizarCheckIn(anyLong(), anyLong())).thenReturn(InscricaoResponseDTO.fromEntityToResponse(inscricaoMokada));

        mockMvc.perform(post("/checkin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}