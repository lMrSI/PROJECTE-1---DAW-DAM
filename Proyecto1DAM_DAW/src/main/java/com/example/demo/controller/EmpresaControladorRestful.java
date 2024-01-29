package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.demo.exception.EmpresaNotFoundException;
import com.example.demo.model.Empresa;
import com.example.demo.model.EmpresaModelAssembler;
import com.example.demo.repository.EmpresaRepository;
import com.example.demo.repository.OfertaRepository;

@CrossOrigin("*") @RestController @RequestMapping("/apirest/empresas")
@Tag(name="Empresas", description="Peticiones CRUD de empresas")
public class EmpresaControladorRestful {
	// R E P O S  y  P R O P I E D A D E S
	@Autowired private final EmpresaRepository repositoryEmpresa;
	@Autowired private final OfertaRepository repositoryOferta;
	private final EmpresaModelAssembler assemblerEmpresa;


	// C O N S T R U C T O R
	public EmpresaControladorRestful(EmpresaRepository repositoryEmpresa, OfertaRepository repositoryOferta, EmpresaModelAssembler assemblerEmpresa) {
		this.repositoryEmpresa = repositoryEmpresa;
		this.assemblerEmpresa = assemblerEmpresa;
		this.repositoryOferta = repositoryOferta;
	}




	// C R E A T E
	@Operation(summary = "Crear empresa", description = "Crea una nueva empresa")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<?> createEmpresa(@RequestBody Empresa empresa) {
		EntityModel<Empresa> empresaEntityModel = assemblerEmpresa.tooModel(repositoryEmpresa.save(empresa));
		return ResponseEntity
				.created(empresaEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(empresaEntityModel);
	}




	// R E A D
	@Operation(summary = "Listar empresas", description = "Busca y devuelve todas las empresas")
	@PreAuthorize("hasAuthority('READ')")
	@GetMapping("/read")
	public CollectionModel<EntityModel<Empresa>> readEmpresas() {
		List<EntityModel<Empresa>> empresas = repositoryEmpresa.findAll().stream()
			  .map(assemblerEmpresa::toModel)
			  .collect(Collectors.toList());
		return CollectionModel.of(empresas, 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmpresaControladorRestful.class).readEmpresas()).withSelfRel());
	}
	@Operation(summary = "Listar ofertas", description = "Busca y devuelve todas las ofertas")
	@PreAuthorize("hasAuthority('READ')")
	@GetMapping("/read/{id}")
	public EntityModel<Empresa> readEmpresa(@PathVariable int id) {
		Empresa empresa = repositoryEmpresa.findById(id)
				.orElseThrow(() -> new EmpresaNotFoundException(id));
		return assemblerEmpresa.toModel(empresa);
	}




	// U P D A T E
	@Operation(summary = "Modifica empresa", description = "Busca y modifica empresa existente")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateEmpresa(@PathVariable int id, @RequestBody Empresa empresa) {
		Empresa empresaActualizada = 
				repositoryEmpresa
					.findById(id)
					.map( empresaCambiada ->
					{
						empresaCambiada.setNombre(empresa.getNombre());
						empresaCambiada.setSector(empresa.getSector());
						empresaCambiada.setTamaño(empresa.getTamaño());
						empresaCambiada.setTipo(empresa.getTipo());
						empresaCambiada.setUbicacion(empresa.getUbicacion());
						return repositoryEmpresa.save(empresaCambiada);
					})
			    	.orElseGet( () ->
					{
			        	empresa.setIdEmpresa(id);
			        	return repositoryEmpresa.save(empresa);
					});
		EntityModel<Empresa> EmpresaEntityModel = assemblerEmpresa.toModel(empresaActualizada);
		return ResponseEntity
		      .created(EmpresaEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
		      .body(EmpresaEntityModel);
	}




	// D E L E T E
	@Operation(summary = "Eliminar empresa", description = "Busca y elimina empresa")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/delete/{id}")
	ResponseEntity<?> deleteEmpresa(@PathVariable int id) {
		repositoryEmpresa.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}