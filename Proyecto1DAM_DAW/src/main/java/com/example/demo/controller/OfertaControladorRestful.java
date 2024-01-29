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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Oferta;
import com.example.demo.model.OfertaModelAssembler;
import com.example.demo.exception.OfertaNotFoundException;
import com.example.demo.model.Empresa;
import com.example.demo.repository.EmpresaRepository;
import com.example.demo.repository.OfertaRepository;

@CrossOrigin("*") @RestController @RequestMapping("/apirest/ofertas")
@Tag(name="Ofertas", description="Peticiones CRUD de ofertas")
public class OfertaControladorRestful {
	// R E P O S  y  P R O P I E D A D E S
	@Autowired
	private final EmpresaRepository repositoryEmpresa;
	@Autowired
	private final OfertaRepository repositoryOferta;
	private final OfertaModelAssembler assemblerOferta;


	// C O N S T R U C T O R
	public OfertaControladorRestful(EmpresaRepository repositoryEmpresa, OfertaRepository repositoryOferta, OfertaModelAssembler assemblerOferta) {
		this.repositoryEmpresa = repositoryEmpresa;
		this.repositoryOferta = repositoryOferta;
		this.assemblerOferta = assemblerOferta;
	}




	// C R E A T E
	@Operation(summary = "Crear oferta", description = "Crea una nueva oferta")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<?> createOferta(@RequestBody Oferta oferta) {
		Oferta savedOferta = repositoryOferta.save(oferta);
		EntityModel<Oferta> ofertaEntityModel = assemblerOferta.toModel(savedOferta);
		return ResponseEntity
				.created(ofertaEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(ofertaEntityModel);
	}
	@Operation(summary = "Crear oferta en empresa", description = "Crea una nueva oferta asociada a empreesa")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/create/by/empresas/{idEmpresa}")
	public ResponseEntity<?> createOfertaByIdEmpresa(@RequestBody Oferta oferta, @PathVariable int idEmpresa) {
		//setOfertaByIdEmpresa()
		oferta.setEmpresa(new Empresa());
		oferta.getEmpresa().setIdEmpresa(idEmpresa);
		EntityModel<Oferta> ofertaEntityModel = assemblerOferta.toModel(repositoryOferta.save(oferta));
		return ResponseEntity
				.created(ofertaEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(ofertaEntityModel);
	}




	// R E A D
	@Operation(summary = "Listar ofertas", description = "Busca y muestra todas las empresas")
	@PreAuthorize("hasAuthority('READ')")
	@GetMapping("/read")
	public CollectionModel<EntityModel<Oferta>> readOfertas() {
		List<EntityModel<Oferta>> ofertas = repositoryOferta.findAll().stream()
				.map(assemblerOferta::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(ofertas,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OfertaControladorRestful.class).readOfertas()).withSelfRel());
	}

	@Operation(summary = "Mostrar 1 oferta", description = "Busca y muestra 1 oferta")
	@PreAuthorize("hasAuthority('READ')")
	@GetMapping("/read/{id}")
	public EntityModel<Oferta> readOferta(@PathVariable int id) {
		Oferta oferta = repositoryOferta.findById(id)
				.orElseThrow(() -> new OfertaNotFoundException(id));
		return assemblerOferta.toModel(oferta);
	}
	@Operation(summary = "Listar ofertas de una empresa", description = "Muestra ofertas de una empresa")
	@PreAuthorize("hasAuthority('READ')")
	@GetMapping("/read/by/empresas/{idEmpresa}")
	public CollectionModel<EntityModel<Oferta>> readOfertasByIdEmpresa(@PathVariable int idEmpresa) {
		//getOfertasByIdEmpresa()
		List<EntityModel<Oferta>> ofertas = repositoryOferta
				.findByEmpresa(repositoryEmpresa.findById(idEmpresa).get())
				.stream()
				.map(assemblerOferta::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(ofertas,
				WebMvcLinkBuilder.linkTo(
						WebMvcLinkBuilder
						.methodOn(OfertaControladorRestful.class)
						.readOfertas()
				).withSelfRel());
	}





	// U P D A T E
	@Operation(summary = "Modifica oferta", description = "Busca y modifica oferta existente")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping("/update/{id}")
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
		EntityModel<Oferta> OfertaEntityModel = assemblerOferta.toModel(ofertaActualizada);

		return ResponseEntity
				.created(OfertaEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
		      	.body(OfertaEntityModel);
	}




	// D E L E T E
	@Operation(summary = "Elimina oferta", description = "Busca y elimina oferta")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/delete/{id}")
	ResponseEntity<?> deleteEmpresa(@PathVariable int id) {
		repositoryOferta.deleteById(id);
		SecurityContextHolder.getContext().getAuthentication();
		return ResponseEntity.noContent().build();
	} 
}
