package com.mx.rcq.api_cars.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.mx.rcq.api_cars.service.ModelService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ModelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModelService modelService;
    
    @Test
    void addModel_ShouldReturnError_WhenPriceIsTooLow() throws Exception {
        mockMvc.perform(post("/brands/1/models")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Toyota\", \"average_price\": 900000}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The average_price must be greater than 100,000."));
    }
}
