package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Oferta;
import com.example.demo.model.OfertaModelAssembler;
import com.example.demo.exception.EmpresaNotFoundException;
import com.example.demo.exception.OfertaNotFoundException;
import com.example.demo.model.Empresa;
import com.example.demo.model.EmpresaModelAssembler;
import com.example.demo.repository.EmpresaRepository;
import com.example.demo.repository.OfertaRepository;

import org.springframework.http.ResponseEntity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("")
@Tag(name="Ofertas", description="APIRESTful de ofertas con operaciones CRUD")
public class OfertaControladorRestful {
	
	
	@Autowired
	private final EmpresaRepository repositoryEmpresa;
	@Autowired
	private final OfertaRepository repositoryOferta;
	
	private final OfertaModelAssembler assembler;
	
	public OfertaControladorRestful(EmpresaRepository repositoryEmpresa, OfertaRepository repositoryOferta, OfertaModelAssembler assembler) {
		this.repositoryEmpresa = repositoryEmpresa;
		this.repositoryOferta = repositoryOferta;
		this.assembler = assembler;
		
	}
	
	@GetMapping("/apirestful/empresas/{idEmpresa}/ofertas")
	public CollectionModel<EntityModel<Oferta>> readOfertasByIdEmpresa(@PathVariable int idEmpresa) {
		List<EntityModel<Oferta>> ofertas = repositoryOferta
				.findByEmpresa(repositoryEmpresa.findById(idEmpresa).get())
				.stream().map(assembler::toModel)
				.collect(Collectors.toList());
		//.findByIdEmpresa(idEmpresa);

		return CollectionModel.of(ofertas,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OfertaControladorRestful.class).readOfertas()).withSelfRel());
	}
	
	@GetMapping("/apirestful/ofertas")	
	public CollectionModel<EntityModel<Oferta>> readOfertas() {
		List<EntityModel<Oferta>> ofertas = repositoryOferta.findAll().stream()
			  .map(assembler::toModel)
			  .collect(Collectors.toList());
		return CollectionModel.of(ofertas, 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OfertaControladorRestful.class).readOfertas()).withSelfRel());
	}
	
	@GetMapping("/apirestful/ofertas/{id}")
	public EntityModel<Oferta> readOferta(@PathVariable int id) {
		Oferta oferta = repositoryOferta.findById(id)
				.orElseThrow(() -> new OfertaNotFoundException(id));
		SecurityContextHolder.getContext().getAuthentication();
		return assembler.toModel(oferta);
	}
	
	@PostMapping("/apirestful/ofertas")
	public ResponseEntity<?> createOferta(@RequestBody Oferta oferta) {
		Oferta savedOferta = repositoryOferta.save(oferta);
		EntityModel<Oferta> ofertaEntityModel = assembler.toModel(savedOferta);
		SecurityContextHolder.getContext().getAuthentication();
		return ResponseEntity
				.created(ofertaEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(ofertaEntityModel);
	}

	@PostMapping("/apirestful/empresas/{idEmpresa}/ofertas")
	public ResponseEntity<?> createOfertaWithIdEmpresa(@RequestBody Oferta oferta, @PathVariable int idEmpresa) {
		oferta.setEmpresa(new Empresa());
		oferta.getEmpresa().setIdEmpresa(idEmpresa);
		//SetOfertaByIdEmpresa()
		EntityModel<Oferta> ofertaEntityModel = assembler.toModel(repositoryOferta.save(oferta));
		SecurityContextHolder.getContext().getAuthentication();
		return ResponseEntity
				.created(ofertaEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(ofertaEntityModel);
	}
	
	
	
	@PutMapping("/apirestful/ofertas/{id}")
	public ResponseEntity<?> updateOferta(@PathVariable int id, @RequestBody Oferta oferta) {
		Oferta ofertaActualizada = 
				repositoryOferta.findById(id)
				.map(ofertaCambiada -> {
					ofertaCambiada.setEmpresa(oferta.getEmpresa());
					ofertaCambiada.setDescripcion(oferta.getDescripcion());
					ofertaCambiada.setFunciones(oferta.getFunciones());
					ofertaCambiada.setTipoContrato(oferta.getTipoContrato());
					ofertaCambiada.setTitulo(oferta.getTitulo());
					SecurityContextHolder.getContext().getAuthentication();
					return repositoryOferta.save(ofertaCambiada);
				})
			    .orElseGet( () -> {
			        oferta.setIdOferta(id);
					SecurityContextHolder.getContext().getAuthentication();
			        return repositoryOferta.save(oferta);
		    	});
		
		EntityModel<Oferta> OfertaEntityModel = assembler.toModel(ofertaActualizada);
		SecurityContextHolder.getContext().getAuthentication();
		return ResponseEntity
				.created(OfertaEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
		      	.body(OfertaEntityModel);
	}
	
	@DeleteMapping("/apirestful/ofertas/{id}")
	ResponseEntity<?> deleteEmpresa(@PathVariable int id) {
		repositoryOferta.deleteById(id);
		SecurityContextHolder.getContext().getAuthentication();
		return ResponseEntity.noContent().build();
	} 
}
