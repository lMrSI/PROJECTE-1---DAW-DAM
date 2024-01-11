package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.example.demo.exception.EmpresaNotFoundException;
import com.example.demo.model.Empresa;
import com.example.demo.model.EmpresaModelAssembler;
import com.example.demo.model.Oferta;
import com.example.demo.repository.EmpresaRepository;
import com.example.demo.repository.OfertaRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("")
public class EmpresaControladorRestful {
	
	
	@Autowired
	private final EmpresaRepository repository;
	
	@Autowired
	private final OfertaRepository repositoryOferta;
	
	private final EmpresaModelAssembler assembler;
	
	public EmpresaControladorRestful(EmpresaRepository repository, OfertaRepository repositoryOferta, EmpresaModelAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
		this.repositoryOferta = repositoryOferta;
	}
	
	
	@GetMapping("/apirestful/empresas")	
	public CollectionModel<EntityModel<Empresa>> readEmpresas() {
		List<EntityModel<Empresa>> empresas = repository.findAll().stream()
			  .map(assembler::toModel)
			  .collect(Collectors.toList());
		SecurityContextHolder.getContext().getAuthentication();
		return CollectionModel.of(empresas, 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmpresaControladorRestful.class).readEmpresas()).withSelfRel());
	}
	
		
	
	
	
	@GetMapping("/apirestful/empresas/{id}")
	public EntityModel<Empresa> readEmpresa(@PathVariable int id) {
		Empresa empresa = repository.findById(id)
				.orElseThrow(() -> new EmpresaNotFoundException(id));
		SecurityContextHolder.getContext().getAuthentication();
		return assembler.toModel(empresa);
	}
	
	
	
	
	@PostMapping("/apirestful/empresas")
	public ResponseEntity<?> createEmpresa(@RequestBody Empresa empresa) {
		EntityModel<Empresa> empresaEntityModel = assembler.toModel(repository.save(empresa));
		SecurityContextHolder.getContext().getAuthentication();
		return ResponseEntity
				.created(empresaEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(empresaEntityModel);
	}
	
	@PostMapping("/apirestful/empresas/ofertas")
	public ResponseEntity<?> createEmpresaAndOfertas(@RequestBody Empresa empresa) {
		repository.save(empresa);
		Empresa ultimaEmpresa = repository.findTopByOrderByIdEmpresaDesc();
		List<Oferta> ultimasOfertas = repositoryOferta.findByEmpresaIsNull();
		for (Oferta oferta : ultimasOfertas) {
            oferta.setEmpresa(ultimaEmpresa);
		}
        repositoryOferta.saveAll(ultimasOfertas);
            
        
        EntityModel<Empresa> empresaEntityModel = assembler.toModel(ultimaEmpresa);
		SecurityContextHolder.getContext().getAuthentication();
		return ResponseEntity.created(empresaEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(empresaEntityModel);
	}
	
	
	
	
	
	@PutMapping("/apirestful/empresas/{id}")
	public ResponseEntity<?> updateEmpresa(@PathVariable int id, @RequestBody Empresa empresa) {
		Empresa empresaActualizada = 
				repository.findById(id)
				.map(empresaCambiada -> {
			    	empresaCambiada.setNombre(empresa.getNombre());
					empresaCambiada.setSector(empresa.getSector());
					empresaCambiada.setTamaño(empresa.getTamaño());
					empresaCambiada.setTipo(empresa.getTipo());
					empresaCambiada.setUbicacion(empresa.getUbicacion());
					return repository.save(empresaCambiada);
				})
			    .orElseGet( () -> {
			        empresa.setIdEmpresa(id);
			        return repository.save(empresa);
		    	});
		
		EntityModel<Empresa> EmpresaEntityModel = assembler.toModel(empresaActualizada);
		SecurityContextHolder.getContext().getAuthentication();
		return ResponseEntity
				.created(EmpresaEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
		      	.body(EmpresaEntityModel);
	}
	
	
	
	
	@DeleteMapping("/apirestful/empresas/{id}")
	ResponseEntity<?> deleteEmpresa(@PathVariable int id) {
		repository.deleteById(id);
		SecurityContextHolder.getContext().getAuthentication();
		return ResponseEntity.noContent().build();
	}
	
}
