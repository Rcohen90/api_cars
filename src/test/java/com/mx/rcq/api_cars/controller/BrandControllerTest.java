package com.mx.rcq.api_cars.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.mx.rcq.api_cars.models.Brand;
import com.mx.rcq.api_cars.service.BrandService;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BrandControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BrandService brandService;

    @Test
    void getAllBrands_ShouldReturnEmptyList() throws Exception {
        Mockito.when(brandService.getAllBrands()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/brands"))
                .andExpect(status().isOk());
    }

    @Test
    void addBrand_ShouldReturnCreatedBrand() throws Exception {
        Brand brand = new Brand("Toyota");
        Mockito.when(brandService.addBrand(Mockito.anyString())).thenReturn(brand);

        mockMvc.perform(post("/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Toyota\"}"))
                .andExpect(status().isOk());
    }
}
