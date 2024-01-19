package com.example.demo.controller;

import com.example.demo.model.Empresa;
import com.example.demo.model.Oferta;
import com.example.demo.model.OfertaModelAssembler;
import com.example.demo.repository.EmpresaRepository;
import com.example.demo.repository.OfertaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class OfertaControladorRestfulTest {

    @InjectMocks
    private OfertaControladorRestful ofertaControladorRestful;

    @Mock
    private EmpresaRepository repositoryEmpresa;

    @Mock
    private OfertaRepository repositoryOferta;

    @Mock
    private OfertaModelAssembler assembler;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetOferta() {
        List<Oferta> ofertas = new ArrayList<>();

        when(repositoryOferta.findAll()).thenReturn(ofertas);
        when(assembler.toModel(any(Oferta.class))).thenReturn(EntityModel.of(new Oferta()));

        CollectionModel<EntityModel<Oferta>> response = ofertaControladorRestful.readOfertas();

        assertNotNull(response);
    }

    @Test
    void testGetOfertaByI() {
        int idOferta = 1;
        Oferta oferta = new Oferta();

        when(repositoryOferta.findById(idOferta)).thenReturn(Optional.of(oferta));
        when(assembler.toModel(oferta)).thenReturn(EntityModel.of(oferta));

        EntityModel<Oferta> response = ofertaControladorRestful.readOferta(idOferta);

        assertNotNull(response);
    }

    @Test
    void testGetOfertaEmpresaById() {
        int idEmpresa = 1;
        List<Oferta> ofertas = new ArrayList<>();

        when(repositoryEmpresa.findById(idEmpresa)).thenReturn(Optional.of(new Empresa()));
        when(repositoryOferta.findByEmpresa(any())).thenReturn(ofertas);
        when(assembler.toModel(any(Oferta.class))).thenReturn(EntityModel.of(new Oferta()));

        CollectionModel<EntityModel<Oferta>> response = ofertaControladorRestful.readOfertasByIdEmpresa(idEmpresa);

        assertNotNull(response);
    }

    //@Test
    void testPostOferta() {
        Oferta ofertaNueva = new Oferta();

        when(repositoryOferta.save(any(Oferta.class))).thenReturn(ofertaNueva);
        when(assembler.toModel(ofertaNueva)).thenReturn(EntityModel.of(ofertaNueva));

        ResponseEntity<?> responseEntity = ofertaControladorRestful.createOferta(ofertaNueva);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // Verifica que el método del repositorio fue llamado con la nueva oferta
        verify(repositoryOferta, times(1)).save(eq(ofertaNueva));
        verify(assembler, times(1)).toModel(eq(ofertaNueva));
    }

    //@Test
    void testPutOferta() {
        // Dado
        int idOferta = 1;
        Oferta ofertaExistente = new Oferta();
        Oferta ofertaActualizada = new Oferta();

        when(repositoryOferta.findById(idOferta)).thenReturn(Optional.of(ofertaExistente));
        when(repositoryOferta.save(any(Oferta.class))).thenReturn(ofertaActualizada);
        when(assembler.toModel(eq(ofertaActualizada))).thenReturn(EntityModel.of(ofertaActualizada));

        ResponseEntity<?> responseEntity = ofertaControladorRestful.updateOferta(idOferta, ofertaActualizada);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // Verifica que el método del repositorio fue llamado con la oferta actualizada
        verify(repositoryOferta, times(1)).findById(eq(idOferta));
        verify(repositoryOferta, times(1)).save(eq(ofertaActualizada));
        verify(assembler, times(1)).toModel(eq(ofertaActualizada));
    }
    @Test
    void testDeleteEmpresa() {
        int idEmpresa = 1;

        ResponseEntity<?> responseEntity = ofertaControladorRestful.deleteEmpresa(idEmpresa);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        verify(repositoryOferta, times(1)).deleteById(eq(idEmpresa));
    }
}
