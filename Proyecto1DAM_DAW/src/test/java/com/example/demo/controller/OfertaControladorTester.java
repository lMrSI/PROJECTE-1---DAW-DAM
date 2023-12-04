package com.example.demo.controller;

import com.example.demo.repository.OfertaRepository;
import com.example.demo.service.OfertaServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class OfertaControladorTester {
    //----------------------- MODO 1
    //Controller o SUT
    OfertaControladorTest ofertaControladorTest;
    //Dependecias
    @Mock
    OfertaServiceTest ofertaServiceTest;
    @Mock
    Model model;
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

    @Test
    void testGetOferta() {
    }
}