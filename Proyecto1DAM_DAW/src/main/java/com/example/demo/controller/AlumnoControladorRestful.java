package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.OfertaRepository;
import com.example.demo.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*") @RestController @RequestMapping("/apirest/alumnos")
@Tag(name="Alumnos", description="Peticiones CRUD de Usuarios-Alumno")
public class AlumnoControladorRestful {
    // R E P O S  y  P R O P I E D A D E S
    @Autowired
    private final OfertaRepository repositoryOferta;
    private final UsuarioRepository repositoryUsuario;
    private final OfertaModelAssembler assembler;


    // C O N S T R U C T O R
    public AlumnoControladorRestful(OfertaRepository repositoryOferta, UsuarioRepository repositoryUsuario, OfertaModelAssembler assembler) {
        this.repositoryOferta = repositoryOferta;
        this.repositoryUsuario = repositoryUsuario;
        this.assembler = assembler;
    }





    // R E A D
    @PreAuthorize("hasAuthority('ROLE_ALUMNE')")
    @Operation(summary = "Listar ofertas de alumno", description = "ofertas alumno")
    @GetMapping("/read/{idUsuario}/ofertas")
    //
    public CollectionModel<EntityModel<Oferta>> ofertasAlumno(@PathVariable("idUsuario") Long id) {
        Usuario usuario = repositoryUsuario.findByIdUsuario(id);
        // return usuario
        List<EntityModel<Oferta>> ofertas = usuario.getOfertas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(ofertas,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OfertaControladorRestful.class).readOfertas()).withSelfRel());
    }




    // U P D A T E
    @PreAuthorize("hasAuthority('ROLE_ALUMNE')")
    @Operation(summary = "Añadir alumno a oferta", description = "Inscribe un alumno a una oferta")
    @PutMapping("/update/{idAlumno}/ofertas/{idOferta}")
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
