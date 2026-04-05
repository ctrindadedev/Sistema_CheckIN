package com.checkInProject.bdd.config;

import com.checkInProject.CheckInProjectApplication;
import com.checkInProject.config.TokenConfig;
import com.checkInProject.service.checkin.CheckinService;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@CucumberContextConfiguration
@SpringBootTest(classes = CheckInProjectApplication.class)
@AutoConfigureMockMvc(addFilters = false)
public class CucumberSpringConfiguration {
    @MockitoBean
    private TokenConfig tokenConfig;

    @MockitoBean
    private CheckinService checkinService;

}



