package com.ian.projetointegradoianbs.resources;

// import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ProcedimentosResourcesTeste {
    @Autowired
    MockMvc mockMvc;

    // @Autowired
    // private ObjectMapper objectMapper;

    @MockBean
    private ProcedimentoResource procedimentoResource;

    @Test
    public void mustReturnSuccess_GetAllProcedimentos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/procedimentos/").header("Authorization",
                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb2xhYm9yYWRvciIsInBlcm1pc3NvZXMiOltdLCJpc3MiOiIvYXBpL2xvZ2luIiwiZXhwIjoxNjQ2NTM5MTIyfQ.DdOFt3u8n9rUSlIGe8zSq5jj3QTGAkYj3Vm6E6Acg3VPKbID_6ub3zF1qb5MjKOTkvKr77gNT881aSpZwsFc_w"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
