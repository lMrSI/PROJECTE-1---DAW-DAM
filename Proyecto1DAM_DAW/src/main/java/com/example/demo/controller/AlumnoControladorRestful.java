package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.OfertaRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AlumnoControladorRestful {
    @Autowired
    private final OfertaRepository repositoryOferta;
    private final UsuarioRepository repositoryUsuario;
    private final OfertaModelAssembler assembler;

    public AlumnoControladorRestful(OfertaRepository repositoryOferta, UsuarioRepository repositoryUsuario, OfertaModelAssembler assembler) {
        this.repositoryOferta = repositoryOferta;
        this.repositoryUsuario = repositoryUsuario;
        this.assembler = assembler;
    }
    @GetMapping("/apirestful/alumnos/{id}/ofertas")
    //CollectionModel<EntityModel<Oferta>>
    public Usuario ofertasAlumno(@PathVariable("id") Long id) {
        return repositoryUsuario.findByIdUsuario(id);
        /*Usuario usuario = repositoryUsuario.findByIdUsuario(id);
        List<EntityModel<Oferta>> ofertas = repositoryOferta.findOfertasByUsuarios(usuario).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(ofertas,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OfertaControladorRestful.class).readOfertas()).withSelfRel());*/
    }


    @PutMapping("/apirestful/alumno/{idAlumno}/ofertas/{idOferta}")
    public ResponseEntity<?> createAlumnoOferta(@PathVariable("idAlumno") long idUsuario,
                                                @PathVariable("idOferta") int idOferta) {
        Oferta oferta = repositoryOferta.getById(idOferta);
        Usuario alumno = repositoryUsuario.findByIdUsuario(idUsuario);
        oferta.getUsuarios().add(alumno);
        EntityModel<Oferta> ofertaEntityModel = assembler.toModel(repositoryOferta.save(oferta));
        return ResponseEntity
                .created(ofertaEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(ofertaEntityModel);
    }


}
