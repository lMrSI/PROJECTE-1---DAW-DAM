package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.exception.EmpresaNotFoundException;
import com.example.demo.model.Empresa;
import com.example.demo.model.EmpresaModelAssembler;
import com.example.demo.model.Oferta;
import com.example.demo.repository.EmpresaRepository;
import com.example.demo.repository.OfertaRepository;

import jakarta.persistence.EntityNotFoundException;

@CrossOrigin("*")
@RestController
@RequestMapping("")
@Tag(name="Empresas", description="APIRESTful de empresas con operaciones CRUD")
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

	@Operation(summary = "read empresas", description = "Busca y devuelve todas las empresas")
	//@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponses(
			value = {
				@ApiResponse(
					responseCode = "200",
					description = "Se devuelve con éxito una lista de empresas",
					content = {
						@Content(
							mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = Empresa.class) )
						)
					}
				)
		    }
	)
	//@Secured("ROLE_ADMIN")
	//@RolesAllowed({"ROLE_ADMIN", "ROLE_PROFE", "ROLE_ALUMNE"})
	//@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_PROFE') or hasAuthority('ROLE_ALUMNE')")
	@PreAuthorize("hasAuthority('READ')")
	@GetMapping("/apirestful/empresas")	
	public CollectionModel<EntityModel<Empresa>> readEmpresas() {
		List<EntityModel<Empresa>> empresas = repository.findAll().stream()
			  .map(assembler::toModel)
			  .collect(Collectors.toList());
		return CollectionModel.of(empresas, 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmpresaControladorRestful.class).readEmpresas()).withSelfRel());
	}




	@PreAuthorize("hasAuthority('READ')")
	@GetMapping("/apirestful/empresas/{id}")
	public EntityModel<Empresa> readEmpresa(@PathVariable int id) {
		Empresa empresa = repository.findById(id)
				.orElseThrow(() -> new EmpresaNotFoundException(id));
		return assembler.toModel(empresa);
	}




	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/apirestful/empresas")
	public ResponseEntity<?> createEmpresa(@RequestBody Empresa empresa) {
		EntityModel<Empresa> empresaEntityModel = assembler.toModel(repository.save(empresa));
		return ResponseEntity
				.created(empresaEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(empresaEntityModel);
	}




	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
		return ResponseEntity.created(empresaEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(empresaEntityModel);
	}




	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping("/apirestful/empresas/{id}")
	public ResponseEntity<?> updateEmpresa(@PathVariable int id, @RequestBody Empresa empresa) {
		Empresa empresaActualizada = 
				repository
					.findById(id)
					.map( empresaCambiada ->
					{
						empresaCambiada.setNombre(empresa.getNombre());
						empresaCambiada.setSector(empresa.getSector());
						empresaCambiada.setTamaño(empresa.getTamaño());
						empresaCambiada.setTipo(empresa.getTipo());
						empresaCambiada.setUbicacion(empresa.getUbicacion());
						return repository.save(empresaCambiada);
					})
			    	.orElseGet( () ->
					{
			        	empresa.setIdEmpresa(id);
			        	return repository.save(empresa);
					});
		EntityModel<Empresa> EmpresaEntityModel = assembler.toModel(empresaActualizada);
		return ResponseEntity
		      .created(EmpresaEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
		      .body(EmpresaEntityModel);
	}




	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/apirestful/empresas/{id}")
	ResponseEntity<?> deleteEmpresa(@PathVariable int id) {
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}