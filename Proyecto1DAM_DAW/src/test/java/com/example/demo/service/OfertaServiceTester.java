package com.example.demo.service;

import com.example.demo.model.Oferta;
import com.example.demo.repository.OfertaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.example.demo.controller.OfertaControladorRestful;

//@ExtendWith() //MODO 2
class OfertaServiceTester {
    //----------------------- MODO 1
    //Datasource o SUT
    @InjectMocks
    OfertaServiceTest ofertaServiceTest;
    //Dependecias
    @Mock
    OfertaRepository repositoryOferta;
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
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
        // Configurar el comportamiento simulado del repositorio
        int idOfertaExistente = 1;
        Oferta ofertaExistente = new Oferta();
        ofertaExistente.setIdOferta(idOfertaExistente);

        Mockito.when(repositoryOferta.findById(idOfertaExistente)).thenReturn(Optional.of(ofertaExistente));

        // Llamar al método del servicio
        Optional<Oferta> ofertaOptional = ofertaServiceTest.findById(idOfertaExistente);

        // Verificar que la oferta existe
        Assertions.assertTrue(ofertaOptional.isPresent());

        // Verificar que la oferta devuelta es la correcta
        Oferta ofertaRecuperada = ofertaOptional.get();
        Assertions.assertEquals(idOfertaExistente, ofertaRecuperada.getIdOferta());

        // Verificar que el método del repositorio fue llamado
        Mockito.verify(repositoryOferta, Mockito.times(1)).findById(idOfertaExistente);
    }

    @Test
    void testCreateOferta() {

    }
    @Test
    void findDelete(){
        int idOfertaExistente = 1;
    }
}