package com.example.demo.controller;

import com.example.demo.controller.OfertaControladorRestful;
import com.example.demo.model.Oferta;
import com.example.demo.repository.OfertaRepository;
import com.example.demo.service.OfertaServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OfertaControladorTester {
    //----------------------- MODO 1
    //Controller o SUT
    @InjectMocks
    OfertaControladorTest ofertaControladorTest;
    //Dependecias
    @Mock
    OfertaServiceTest ofertaServiceTest;
    @Mock
    Model model;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        ofertaControladorTest = new OfertaControladorTest(ofertaServiceTest);
    }
    @Test
    void getOfertas() {
        when(ofertaServiceTest.findAll()).thenReturn(List.of());
        ofertaControladorTest.getOfertas(model);
    }
    /*
    @Test
    void testReadOfertasByIdEmpresa() throws Exception {
        int idEmpresa = 1;
        List<Oferta> ofertas = Arrays.asList(new Oferta(), new Oferta());
        when(ofertaServiceTest.findById(idEmpresa)).thenReturn(ofertas);
        ofertaServiceTest.findAll();
    }
     */
}