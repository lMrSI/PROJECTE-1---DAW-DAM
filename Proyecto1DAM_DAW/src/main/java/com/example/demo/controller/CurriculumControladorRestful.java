package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.exception.CurriculumNotFoundException;
import com.example.demo.model.Curriculum;
import com.example.demo.model.CurriculumModelAssembler;
import com.example.demo.model.Status;
import com.example.demo.repository.CurriculumRepository;

@CrossOrigin("*") @RestController @RequestMapping("/apirest/curriculums")
@Tag(name="Curriculums", description="Peticiones CRUD de curriculums")
public class CurriculumControladorRestful {
	// R E P O S  y  P R O P I E D A D E S
	@Autowired
	private final CurriculumRepository repositoryCurriculum;
	private final CurriculumModelAssembler assemblerCurriculum;


	// C O N S T R U C T O R
	public CurriculumControladorRestful(CurriculumRepository repositoryCurriculum, CurriculumModelAssembler assemblerCurriculum) {
		this.repositoryCurriculum = repositoryCurriculum;
		this.assemblerCurriculum = assemblerCurriculum;
	}




	// R E A D
	@PreAuthorize("hasAuthority('ROLE_PROFE')")
	@Operation(summary = "Listar CVs")
	@GetMapping("/read")
	public CollectionModel<EntityModel<Curriculum>> readCurriculums() {
		List<EntityModel<Curriculum>> curriculums = repositoryCurriculum.findAll().stream()
			  .map(assemblerCurriculum::toModel)
			  .collect(Collectors.toList());
		return CollectionModel.of(curriculums, 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CurriculumControladorRestful.class).readCurriculums()).withSelfRel());
	}
	@PreAuthorize("hasAuthority('ROLE_PROFE')")
	@Operation(summary = "Mostrar CV")
	@GetMapping("/read/{id}")
	public EntityModel<Curriculum> readCurriculum(@PathVariable int id) {
		Curriculum curriculum = repositoryCurriculum.findById(id)
				.orElseThrow(() -> new CurriculumNotFoundException(id));
		return assemblerCurriculum.toModel(curriculum);
	}




	// C R E A T E
	@PreAuthorize("hasAuthority('ROLE_ALUMNE')")
	@Operation(summary = "Crear CV")
	@PostMapping("/create")
	public ResponseEntity<EntityModel<Curriculum>> createCurriculum(@RequestBody Curriculum curriculum) {
		curriculum.setStatus(Status.CV_SUBIDO);
		Curriculum nuevoCurriculum = repositoryCurriculum.save(curriculum);
		return ResponseEntity
				.created(
						WebMvcLinkBuilder.linkTo(
								WebMvcLinkBuilder.methodOn(CurriculumControladorRestful.class)
								.readCurriculum(nuevoCurriculum.getIdCurriculum())
						)
						.toUri()
				)
				.body(assemblerCurriculum.toModel(nuevoCurriculum));
	}




	// U P D A T E: validar
	@PreAuthorize("hasAuthority('ROLE_PROFE')")
	@Operation(summary = "Validad CV")
	@PutMapping("/validar/{id}")
	public ResponseEntity<?> validarCurriculum(@PathVariable int id) {
		Curriculum curriculumActualizado = 
				repositoryCurriculum.findById(id)
			    .orElseThrow(() -> new CurriculumNotFoundException(id));
		if (curriculumActualizado.getStatus() == Status.CV_SUBIDO) {
			curriculumActualizado.setStatus(Status.CV_VALIDADO);
		}
		return ResponseEntity.ok(assemblerCurriculum.toModel(repositoryCurriculum.save(curriculumActualizado)));
	}



	@PreAuthorize("hasAuthority('ROLE_PROFE')")
	@Operation(summary = "Invalidar CV")
	@DeleteMapping("/invalidar/{id}")
	public ResponseEntity<?> invalidarCurriculum(@PathVariable int id) {
		Curriculum curriculumActualizado = 
				repositoryCurriculum.findById(id)
			    .orElseThrow(() -> new CurriculumNotFoundException(id));
		if (curriculumActualizado.getStatus() == Status.CV_SUBIDO) {
			curriculumActualizado.setStatus(Status.CV_INVALIDADO);
			return ResponseEntity.ok(assemblerCurriculum.toModel(repositoryCurriculum.save(curriculumActualizado)));
		}

		return ResponseEntity
		.status(HttpStatus.METHOD_NOT_ALLOWED)
		.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
		.body(Problem.create()
		.withTitle("Imposible invalidar")
		.withDetail("El CV esta en estado: " + curriculumActualizado.getStatus()));
	}
}


