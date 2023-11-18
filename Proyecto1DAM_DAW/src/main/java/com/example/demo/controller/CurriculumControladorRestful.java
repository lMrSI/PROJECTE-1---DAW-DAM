package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.CurriculumNotFoundException;
import com.example.demo.model.Curriculum;
import com.example.demo.model.CurriculumModelAssembler;
import com.example.demo.model.Status;
import com.example.demo.repository.CurriculumRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("")
public class CurriculumControladorRestful {
	
	
	@Autowired
	private final CurriculumRepository repository;
	
	private final CurriculumModelAssembler assembler;
	
	public CurriculumControladorRestful(CurriculumRepository repository, CurriculumModelAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}
	
	
	@GetMapping("/apirestful/curriculums")	
	public CollectionModel<EntityModel<Curriculum>> readCurriculums() {
		List<EntityModel<Curriculum>> curriculums = repository.findAll().stream()
			  .map(assembler::toModel)
			  .collect(Collectors.toList());
		return CollectionModel.of(curriculums, 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CurriculumControladorRestful.class).readCurriculums()).withSelfRel());
	}
	
		
	
	
	
	@GetMapping("/apirestful/curriculums/{id}")
	public EntityModel<Curriculum> readCurriculum(@PathVariable int id) {
		Curriculum curriculum = repository.findById(id)
				.orElseThrow(() -> new CurriculumNotFoundException(id));
		return assembler.toModel(curriculum);
	}
	
	
	
	
	@PostMapping("/apirestful/curriculums")
	public ResponseEntity<EntityModel<Curriculum>> createCurriculum(@RequestBody Curriculum curriculum) {
		curriculum.setStatus(Status.CV_SUBIDO);
		Curriculum nuevoCurriculum = repository.save(curriculum);
		return ResponseEntity.created(
						WebMvcLinkBuilder.linkTo(
								WebMvcLinkBuilder.methodOn(
										CurriculumControladorRestful.class
								)
								.readCurriculum(
										nuevoCurriculum.getIdCurriculum()
								)
						)
						.toUri()
				)
				.body(assembler.toModel(nuevoCurriculum));
	}
	
	
	
	
	@PutMapping("/apirestful/curriculums/{id}/validar_cv")
	public ResponseEntity<?> validarCurriculum(@PathVariable int id) {
		Curriculum curriculumActualizado = 
				repository.findById(id)
			    .orElseThrow(() -> new CurriculumNotFoundException(id));
		
		if (curriculumActualizado.getStatus() == Status.CV_SUBIDO) {
			curriculumActualizado.setStatus(Status.CV_VALIDADO);
		}
		return ResponseEntity.ok(assembler.toModel(repository.save(curriculumActualizado)));
	}
	
	
	

	@DeleteMapping("/apirestful/curriculums/{id}/invalidar_cv")
	public ResponseEntity<?> invalidarCurriculum(@PathVariable int id) {
		Curriculum curriculumActualizado = 
				repository.findById(id)
			    .orElseThrow(() -> new CurriculumNotFoundException(id));
		
		if (curriculumActualizado.getStatus() == Status.CV_SUBIDO) {
			curriculumActualizado.setStatus(Status.CV_INVALIDADO);
			return ResponseEntity.ok(assembler.toModel(repository.save(curriculumActualizado)));
		}
		
		return ResponseEntity
		.status(HttpStatus.METHOD_NOT_ALLOWED)
		.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
		.body(Problem.create()
		.withTitle("Imposible invalidar")
		.withDetail("El CV esta en estado: " + curriculumActualizado.getStatus()));
	}
}


