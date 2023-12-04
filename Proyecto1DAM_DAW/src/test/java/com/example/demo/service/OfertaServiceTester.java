package com.example.demo.service;

import com.example.demo.model.Oferta;
import com.example.demo.repository.OfertaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@ExtendWith() //MODO 2
class OfertaServiceTester {
    //----------------------- MODO 1
    //Datasource o SUT
    OfertaServiceTest ofertaServiceTest;
    //Dependecias
    @Mock
    OfertaRepository repositoryOferta;
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        ofertaServiceTest = new OfertaServiceTest(repositoryOferta);
    }

    //--------------------- MODO 2
    //SUT
    //@InjectMocks
    //---------------------

    @Test
    void findAll() {
        //given

        //when
        when(repositoryOferta.findAll())
        //then
        .thenReturn(List.of());

        List<Oferta> ofertas = repositoryOferta.findAll();
        // comprobaciones JUnit
        assertNotNull(ofertas);
        assertEquals(0, ofertas.size());
        // comprobaciones Mockito
        //verify(repositoryOferta, atLeast(1)).findAll();
        //verify(repositoryOferta, times(2)).findAll();
        //verify(repositoryOferta, never()).findAll();
    }

    @Test
    void findById() {

    }
}